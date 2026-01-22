package com.ams.controller;

import com.ams.config.Constants;
import com.ams.model.Category;

import com.ams.service.CategoryService;
import com.ams.service.CategoryServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CategoryServlet", urlPatterns = { "/categories" })
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check Authentication & Authorization
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        if (!Constants.ROLE_FINANCE_HEAD.equals(roleName)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listCategories(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        String roleName = (String) session.getAttribute(Constants.SESSION_ROLE);
        if (!Constants.ROLE_FINANCE_HEAD.equals(roleName)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, Constants.MSG_ACCESS_DENIED);
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "create":
                createCategory(request, response);
                break;
            case "update":
                updateCategory(request, response);
                break;
            case "delete":
                // Optional: Implement delete if needed
                break;
            default:
                listCategories(request, response);
                break;
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Category> categories;

        if (keyword != null && !keyword.isBlank()) {
            categories = categoryService.searchCategories(keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            categories = categoryService.getAllCategories();
        }

        request.setAttribute("categories", categories);
        request.setAttribute("pageTitle", "Quản lý Danh mục");
        request.setAttribute("currentPage", "categories");

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "category/list" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Thêm Danh mục mới");
        request.setAttribute("currentPage", "categories");
        request.getRequestDispatcher(Constants.VIEW_PREFIX + "category/form" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            Optional<Category> categoryOpt = categoryService.getCategoryById(id);

            if (categoryOpt.isPresent()) {
                request.setAttribute("category", categoryOpt.get());
                request.setAttribute("pageTitle", "Chỉnh sửa Danh mục");
                request.setAttribute("currentPage", "categories");
                request.getRequestDispatcher(Constants.VIEW_PREFIX + "category/form" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy danh mục với ID: " + id);
                listCategories(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            listCategories(request, response);
        }
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("categoryName");
        String prefix = request.getParameter("prefixCode");
        String description = request.getParameter("description");

        Category category = new Category();
        category.setCategoryName(name);
        category.setPrefixCode(prefix);
        category.setDescription(description);

        try {
            categoryService.createCategory(category);
            response.sendRedirect(request.getContextPath() + "/categories?success=create");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("category", category); // keep inputs
            showCreateForm(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi hệ thống");
            showCreateForm(request, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("categoryId");
        String name = request.getParameter("categoryName");
        String description = request.getParameter("description");
        // Prefix cannot be updated according to AC, so we typically don't read it or
        // use the hidden field just for object completeness but service won't update it

        try {
            Integer id = Integer.parseInt(idStr);
            Category category = new Category();
            category.setCategoryId(id);
            category.setCategoryName(name);
            category.setDescription(description);
            // Prefix might be passed as hidden but we won't set it if we follow AC
            // strictness in service (Service ignores it? Service only updates name and
            // desc)

            boolean updated = categoryService.updateCategory(category);

            if (updated) {
                response.sendRedirect(request.getContextPath() + "/categories?success=update");
            } else {
                request.setAttribute("error", "Không thể cập nhật danh mục");
                request.setAttribute("category", category); // keep user inputs
                // Re-fetch category to get prefix code for display because we didn't submit it
                // or need it
                Optional<Category> original = categoryService.getCategoryById(id);
                original.ifPresent(c -> category.setPrefixCode(c.getPrefixCode()));

                showEditForm(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/categories?error=invalid_id");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            // Need to set ID back to keep form in edit mode
            try {
                Integer id = Integer.parseInt(idStr);
                Category cat = new Category();
                cat.setCategoryId(id);
                cat.setCategoryName(name);
                cat.setDescription(description);
                // Need prefix for display
                Optional<Category> original = categoryService.getCategoryById(id);
                original.ifPresent(c -> cat.setPrefixCode(c.getPrefixCode()));

                request.setAttribute("category", cat);
            } catch (Exception ex) {
            }

            showEditForm(request, response);
        }
    }
}
