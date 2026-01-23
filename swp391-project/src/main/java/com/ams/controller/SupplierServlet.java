package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.Supplier;
import com.ams.service.SupplierService;
import com.ams.service.impl.SupplierServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SupplierServlet", urlPatterns = { "/suppliers" })
public class SupplierServlet extends HttpServlet {

    private SupplierService supplierService;

    @Override
    public void init() throws ServletException {
        super.init();
        supplierService = new SupplierServiceImpl();
    }

    private boolean checkAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return false;
        }

        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        if (Constants.ROLE_FINANCE_HEAD.equals(roleName) || Constants.ROLE_ASSET_STAFF.equals(roleName)) {
            return true;
        }

        response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
        return false;
    }

    private boolean canEdit(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return false;
        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        return Constants.ROLE_FINANCE_HEAD.equals(roleName);
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
            case "edit":
                if (!canEdit(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
                    return;
                }
                if ("create".equals(action))
                    showCreateForm(request, response);
                else
                    showEditForm(request, response);
                break;
            case "list":
            default:
                listSuppliers(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!checkAccess(request, response))
            return;

        if (!canEdit(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
            return;
        }

        String action = request.getParameter("action");
        if (action == null)
            action = "list";

        switch (action) {
            case "create":
                createSupplier(request, response);
                break;
            case "update":
                updateSupplier(request, response);
                break;
            default:
                listSuppliers(request, response);
                break;
        }
    }

    private void listSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Supplier> suppliers;

        if (keyword != null && !keyword.isBlank()) {
            suppliers = supplierService.searchSuppliers(keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            suppliers = supplierService.getAllSuppliers();
        }

        request.setAttribute("suppliers", suppliers);
        request.setAttribute("pageTitle", "Quản lý Nhà cung cấp");
        request.setAttribute("currentPage", "suppliers");
        request.setAttribute("canEdit", canEdit(request));

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "supplier/list" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Thêm Nhà cung cấp mới");
        request.setAttribute("currentPage", "suppliers");
        request.getRequestDispatcher(Constants.VIEW_PREFIX + "supplier/form" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            Optional<Supplier> supplierOpt = supplierService.getSupplierById(id);

            if (supplierOpt.isPresent()) {
                request.setAttribute("supplier", supplierOpt.get());
                request.setAttribute("pageTitle", "Chỉnh sửa Nhà cung cấp");
                request.setAttribute("currentPage", "suppliers");
                request.getRequestDispatcher(Constants.VIEW_PREFIX + "supplier/form" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy nhà cung cấp với ID: " + id);
                listSuppliers(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            listSuppliers(request, response);
        }
    }

    private void createSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("supplierName");
        String contact = request.getParameter("contactPerson");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier(null, name, contact, email, phone, address);

        try {
            supplierService.createSupplier(supplier);
            response.sendRedirect(request.getContextPath() + "/suppliers?success=create");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("supplier", supplier);
            showCreateForm(request, response);
        }
    }

    private void updateSupplier(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("supplierId");
        String name = request.getParameter("supplierName");
        String contact = request.getParameter("contactPerson");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Supplier supplier = new Supplier();
        try {
            supplier.setSupplierId(Integer.parseInt(idStr));
            supplier.setSupplierName(name);
            supplier.setContactPerson(contact);
            supplier.setEmail(email);
            supplier.setPhone(phone);
            supplier.setAddress(address);

            boolean updated = supplierService.updateSupplier(supplier);
            if (updated) {
                response.sendRedirect(request.getContextPath() + "/suppliers?success=update");
            } else {
                request.setAttribute("error", "Không thể cập nhật nhà cung cấp");
                request.setAttribute("supplier", supplier);
                showEditForm(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("supplier", supplier);
            showEditForm(request, response);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/suppliers?error=invalid_data");
        }
    }
}
