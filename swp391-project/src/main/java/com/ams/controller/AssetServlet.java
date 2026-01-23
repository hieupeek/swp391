package com.ams.controller;

import com.ams.config.Constants;
import com.ams.dao.CategoryDAO;
import com.ams.dao.CategoryDAOImpl;
import com.ams.dao.RoomDAO;
import com.ams.dao.RoomDAOImpl;
import com.ams.dao.SupplierDAO;
import com.ams.dao.SupplierDAOImpl;
import com.ams.model.Asset;
import com.ams.model.User;
import com.ams.service.AssetService;
import com.ams.service.AssetServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AssetServlet", urlPatterns = { "/assets" })
public class AssetServlet extends HttpServlet {

    private AssetService assetService;
    private CategoryDAO categoryDAO;
    private SupplierDAO supplierDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        assetService = new AssetServiceImpl();
        categoryDAO = new CategoryDAOImpl();
        supplierDAO = new SupplierDAOImpl();
        roomDAO = new RoomDAOImpl();
    }

    private boolean checkAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return false;
        }

        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        // Allow Finance Head and Asset Staff
        if (Constants.ROLE_FINANCE_HEAD.equals(roleName) || Constants.ROLE_ASSET_STAFF.equals(roleName)) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
        return false;
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
            case "edit":
                showEditForm(request, response);
                break;
            case "detail":
                showAssetDetail(request, response);
                break;
            case "list":
            default:
                listAssets(request, response);
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
                createAsset(request, response);
                break;
            case "update":
                updateAsset(request, response);
                break;
            default:
                listAssets(request, response);
                break;
        }
    }

    private void listAssets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        String statusFilter = request.getParameter("status");
        List<Asset> assets;

        if (statusFilter != null && !statusFilter.isBlank()) {
            // Filter by status first
            assets = assetService.getAssetsByStatus(statusFilter);
            request.setAttribute("filterStatus", statusFilter);
        } else if (keyword != null && !keyword.isBlank()) {
            assets = assetService.searchAssets(keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            assets = assetService.getAllAssets();
        }

        request.setAttribute("assets", assets);
        request.setAttribute("pageTitle", "Quản lý Tài sản");
        request.setAttribute("currentPage", "assets");

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "asset/list" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loadFormData(request);
        request.setAttribute("pageTitle", "Thêm Tài sản mới");
        request.setAttribute("currentPage", "assets");
        request.getRequestDispatcher(Constants.VIEW_PREFIX + "asset/form" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            Optional<Asset> assetOpt = assetService.getAssetById(id);

            if (assetOpt.isPresent()) {
                request.setAttribute("asset", assetOpt.get());
                loadFormData(request);
                request.setAttribute("pageTitle", "Chỉnh sửa Tài sản");
                request.setAttribute("currentPage", "assets");
                request.getRequestDispatcher(Constants.VIEW_PREFIX + "asset/form" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy tài sản với ID: " + id);
                listAssets(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            listAssets(request, response);
        }
    }

    private void showAssetDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            Optional<Asset> assetOpt = assetService.getAssetById(id);

            if (assetOpt.isPresent()) {
                request.setAttribute("asset", assetOpt.get());
                request.setAttribute("historyList", assetService.getAssetHistory(id));
                request.setAttribute("pageTitle", "Chi tiết Tài sản");
                request.setAttribute("currentPage", "assets");
                request.getRequestDispatcher(Constants.VIEW_PREFIX + "asset/detail" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy tài sản với ID: " + id);
                listAssets(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            listAssets(request, response);
        }
    }

    private void loadFormData(HttpServletRequest request) {
        request.setAttribute("categories", categoryDAO.findAll());
        request.setAttribute("suppliers", supplierDAO.findAll());
        request.setAttribute("rooms", roomDAO.findAll());
        // Enum values for status
        request.setAttribute("statuses",
                new String[] { "New", "In_Use", "Maintenance", "Broken", "Liquidated", "Lost" });
    }

    private void createAsset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Form Data
        String name = request.getParameter("assetName");
        String categoryIdStr = request.getParameter("categoryId");
        String supplierIdStr = request.getParameter("supplierId");
        String roomIdStr = request.getParameter("roomId");
        String priceStr = request.getParameter("price");
        String purchaseDateStr = request.getParameter("purchaseDate");
        String warrantyDateStr = request.getParameter("warrantyDate");
        String status = request.getParameter("status");
        String description = request.getParameter("description");

        Asset asset = new Asset();
        asset.setAssetName(name);
        asset.setStatus(status);
        asset.setDescription(description);

        try {
            if (categoryIdStr != null && !categoryIdStr.isBlank())
                asset.setCategoryId(Integer.parseInt(categoryIdStr));

            if (supplierIdStr != null && !supplierIdStr.isBlank())
                asset.setSupplierId(Integer.parseInt(supplierIdStr));

            if (roomIdStr != null && !roomIdStr.isBlank())
                asset.setRoomId(Integer.parseInt(roomIdStr));

            if (priceStr != null && !priceStr.isBlank())
                asset.setPrice(new BigDecimal(priceStr));

            if (purchaseDateStr != null && !purchaseDateStr.isBlank())
                asset.setPurchaseDate(LocalDate.parse(purchaseDateStr));

            if (warrantyDateStr != null && !warrantyDateStr.isBlank())
                asset.setWarrantyExpiryDate(LocalDate.parse(warrantyDateStr));

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constants.SESSION_USER);

            assetService.createAsset(asset, user);
            response.sendRedirect(request.getContextPath() + "/assets?success=create");

        } catch (IllegalArgumentException | DateTimeParseException | ArithmeticException e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.setAttribute("asset", asset);
            loadFormData(request);
            showCreateForm(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống");
            loadFormData(request);
            showCreateForm(request, response);
        }
    }

    private void updateAsset(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("assetId");
        String name = request.getParameter("assetName");
        String categoryIdStr = request.getParameter("categoryId");
        String supplierIdStr = request.getParameter("supplierId");
        String roomIdStr = request.getParameter("roomId");
        String priceStr = request.getParameter("price");
        String purchaseDateStr = request.getParameter("purchaseDate");
        String warrantyDateStr = request.getParameter("warrantyDate");
        String status = request.getParameter("status");
        String description = request.getParameter("description");

        Asset asset = new Asset();

        try {
            asset.setAssetId(Integer.parseInt(idStr));
            asset.setAssetName(name);
            asset.setStatus(status);
            asset.setDescription(description);

            if (categoryIdStr != null && !categoryIdStr.isBlank())
                asset.setCategoryId(Integer.parseInt(categoryIdStr));

            if (supplierIdStr != null && !supplierIdStr.isBlank())
                asset.setSupplierId(Integer.parseInt(supplierIdStr));
            else
                asset.setSupplierId(null);

            if (roomIdStr != null && !roomIdStr.isBlank())
                asset.setRoomId(Integer.parseInt(roomIdStr));
            else
                asset.setRoomId(null);

            if (priceStr != null && !priceStr.isBlank())
                asset.setPrice(new BigDecimal(priceStr));

            if (purchaseDateStr != null && !purchaseDateStr.isBlank())
                asset.setPurchaseDate(LocalDate.parse(purchaseDateStr));
            else
                asset.setPurchaseDate(null);

            if (warrantyDateStr != null && !warrantyDateStr.isBlank())
                asset.setWarrantyExpiryDate(LocalDate.parse(warrantyDateStr));
            else
                asset.setWarrantyExpiryDate(null);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Constants.SESSION_USER);

            boolean updated = assetService.updateAsset(asset, user);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/assets?success=update");
            } else {
                request.setAttribute("error", "Không thể cập nhật tài sản");
                request.setAttribute("asset", asset);
                loadFormData(request);
                showEditForm(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/assets?error=invalid_data");
        }
    }
}
