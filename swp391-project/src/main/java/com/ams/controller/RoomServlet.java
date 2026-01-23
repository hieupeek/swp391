package com.ams.controller;

import com.ams.config.Constants;
import com.ams.dao.DepartmentDAO;
import com.ams.dao.DepartmentDAOImpl;

import com.ams.model.Room;
import com.ams.service.RoomService;
import com.ams.service.RoomServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "RoomServlet", urlPatterns = { "/rooms" })
public class RoomServlet extends HttpServlet {

    private RoomService roomService;
    private DepartmentDAO departmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        roomService = new RoomServiceImpl();
        departmentDAO = new DepartmentDAOImpl();
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
                listRooms(request, response);
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
                createRoom(request, response);
                break;
            case "update":
                updateRoom(request, response);
                break;
            default:
                listRooms(request, response);
                break;
        }
    }

    private void listRooms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Room> rooms;

        if (keyword != null && !keyword.isBlank()) {
            rooms = roomService.searchRooms(keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            rooms = roomService.getAllRooms();
        }

        request.setAttribute("rooms", rooms);
        request.setAttribute("pageTitle", "Quản lý Phòng ban/Lớp học");
        request.setAttribute("currentPage", "rooms");
        request.setAttribute("canEdit", canEdit(request));

        request.getRequestDispatcher(Constants.VIEW_PREFIX + "room/list" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("departments", departmentDAO.findAll());
        request.setAttribute("pageTitle", "Thêm Phòng mới");
        request.setAttribute("currentPage", "rooms");
        request.getRequestDispatcher(Constants.VIEW_PREFIX + "room/form" + Constants.VIEW_SUFFIX)
                .forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            Integer id = Integer.parseInt(idStr);
            Optional<Room> roomOpt = roomService.getRoomById(id);

            if (roomOpt.isPresent()) {
                request.setAttribute("room", roomOpt.get());
                request.setAttribute("departments", departmentDAO.findAll());
                request.setAttribute("pageTitle", "Chỉnh sửa Phòng");
                request.setAttribute("currentPage", "rooms");
                request.getRequestDispatcher(Constants.VIEW_PREFIX + "room/form" + Constants.VIEW_SUFFIX)
                        .forward(request, response);
            } else {
                request.setAttribute("error", "Không tìm thấy phòng với ID: " + id);
                listRooms(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
            listRooms(request, response);
        }
    }

    private void createRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("roomName");
        String deptIdStr = request.getParameter("deptId");
        String capacityStr = request.getParameter("capacity");

        Room room = new Room();
        room.setRoomName(name);
        try {
            if (deptIdStr != null && !deptIdStr.isBlank())
                room.setDeptId(Integer.parseInt(deptIdStr));
            if (capacityStr != null && !capacityStr.isBlank())
                room.setCapacity(Integer.parseInt(capacityStr));

            roomService.createRoom(room);
            response.sendRedirect(request.getContextPath() + "/rooms?success=create");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("room", room);
            showCreateForm(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi dữ liệu đầu vào");
            showCreateForm(request, response);
        }
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("roomId");
        String name = request.getParameter("roomName");
        String deptIdStr = request.getParameter("deptId");
        String capacityStr = request.getParameter("capacity");

        Room room = new Room();
        try {
            room.setRoomId(Integer.parseInt(idStr));
            room.setRoomName(name);
            if (deptIdStr != null && !deptIdStr.isBlank())
                room.setDeptId(Integer.parseInt(deptIdStr));
            if (capacityStr != null && !capacityStr.isBlank())
                room.setCapacity(Integer.parseInt(capacityStr));

            boolean updated = roomService.updateRoom(room);
            if (updated) {
                response.sendRedirect(request.getContextPath() + "/rooms?success=update");
            } else {
                request.setAttribute("error", "Không thể cập nhật phòng");
                request.setAttribute("room", room);
                showEditForm(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("room", room);
            showEditForm(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/rooms?error=invalid_data");
        }
    }
}
