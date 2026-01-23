package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.Asset;
import com.ams.model.LiquidationDetail;
import com.ams.model.LiquidationMinute;
import com.ams.model.User;
import com.ams.service.LiquidationService;
import com.ams.service.LiquidationServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servlet xử lý nghiệp vụ Thanh lý tài sản.
 * 
 * Actions:
 * - list: Danh sách biên bản thanh lý
 * - create: Form tạo biên bản mới
 * - view: Xem chi tiết biên bản
 * - approve: Duyệt biên bản (Principal only)
 * - reject: Từ chối biên bản (Principal only)
 */
@WebServlet(name = "LiquidationServlet", urlPatterns = { "/liquidation" })
public class LiquidationServlet extends HttpServlet {

    private LiquidationService liquidationService;

    @Override
    public void init() throws ServletException {
        super.init();
        liquidationService = new LiquidationServiceImpl();
    }

    /**
     * Kiểm tra quyền truy cập.
     * - Finance Head, Asset Staff: Tạo biên bản, xem danh sách
     * - Principal: Duyệt/Từ chối
     */
    private boolean checkAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return false;
        }

        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        if (Constants.ROLE_PRINCIPAL.equals(roleName) ||
                Constants.ROLE_FINANCE_HEAD.equals(roleName) ||
                Constants.ROLE_ASSET_STAFF.equals(roleName)) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
        return false;
    }

    private boolean isPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return false;
        String role = (String) session.getAttribute(Constants.SESSION_ROLE);
        return Constants.ROLE_PRINCIPAL.equals(role);
    }

    private boolean canCreate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return false;
        String role = (String) session.getAttribute(Constants.SESSION_ROLE);
        return Constants.ROLE_FINANCE_HEAD.equals(role) || Constants.ROLE_ASSET_STAFF.equals(role);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkAccess(request, response))
            return;

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "view":
                viewMinute(request, response);
                break;
            case "list":
            default:
                listMinutes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkAccess(request, response))
            return;

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "create":
                createMinute(request, response);
                break;
            case "approve":
                approveMinute(request, response);
                break;
            case "reject":
                rejectMinute(request, response);
                break;
            default:
                listMinutes(request, response);
                break;
        }
    }

    private void listMinutes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<LiquidationMinute> minutes = liquidationService.getAllMinutes();
        request.setAttribute("minutes", minutes);
        request.setAttribute("pageTitle", "Quản lý Thanh lý");
        request.setAttribute("currentPage", "liquidation");
        request.setAttribute("isPrincipal", isPrincipal(request));

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "liquidation/list" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!canCreate(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Chỉ Finance Head hoặc Asset Staff mới có thể tạo biên bản");
            return;
        }

        List<Asset> eligibleAssets = liquidationService.getAssetsEligibleForLiquidation();
        request.setAttribute("eligibleAssets", eligibleAssets);
        request.setAttribute("pageTitle", "Tạo Biên bản Thanh lý");
        request.setAttribute("currentPage", "liquidation");

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "liquidation/form" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void viewMinute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            Optional<LiquidationMinute> opt = liquidationService.getMinuteById(id);

            if (opt.isPresent()) {
                LiquidationMinute minute = opt.get();
                List<LiquidationDetail> details = liquidationService.getDetailsByMinuteId(id);

                request.setAttribute("minute", minute);
                request.setAttribute("details", details);
                request.setAttribute("pageTitle", "Chi tiết Biên bản #" + id);
                request.setAttribute("currentPage", "liquidation");
                request.setAttribute("isPrincipal", isPrincipal(request));

                request.getRequestDispatcher(Constants.VIEW_PREFIX + "liquidation/detail" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/liquidation?error=notfound");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/liquidation?error=invalid");
        }
    }

    private void createMinute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!canCreate(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String note = request.getParameter("note");
        String[] assetIdStrs = request.getParameterValues("assetIds");
        String[] reasons = request.getParameterValues("reasons");

        if (assetIdStrs == null || assetIdStrs.length == 0) {
            request.setAttribute("error", "Vui lòng chọn ít nhất một tài sản");
            showCreateForm(request, response);
            return;
        }

        List<Integer> assetIds = new ArrayList<>();
        List<String> reasonList = new ArrayList<>();
        for (int i = 0; i < assetIdStrs.length; i++) {
            assetIds.add(Integer.parseInt(assetIdStrs[i]));
            reasonList.add(reasons != null && i < reasons.length ? reasons[i] : "");
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);

        try {
            liquidationService.createMinute(note, assetIds, reasonList, user);
            response.sendRedirect(request.getContextPath() + "/liquidation?success=create");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            showCreateForm(request, response);
        }
    }

    private void approveMinute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!isPrincipal(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ Principal mới có quyền duyệt");
            return;
        }

        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constants.SESSION_USER);

            boolean success = liquidationService.approveMinute(id, user);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/liquidation?success=approve");
            } else {
                response.sendRedirect(request.getContextPath() + "/liquidation?error=approve_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/liquidation?error=invalid");
        }
    }

    private void rejectMinute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!isPrincipal(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Chỉ Principal mới có quyền từ chối");
            return;
        }

        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constants.SESSION_USER);

            boolean success = liquidationService.rejectMinute(id, user);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/liquidation?success=reject");
            } else {
                response.sendRedirect(request.getContextPath() + "/liquidation?error=reject_failed");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/liquidation?error=invalid");
        }
    }
}
