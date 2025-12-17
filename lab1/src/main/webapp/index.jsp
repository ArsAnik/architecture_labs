<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.booking.entity.Client, com.booking.entity.Booking, com.booking.entity.BookingStatus" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Система бронирования</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body class="bg-light">

<div class="container my-4">
    <h1 class="mb-4">Система бронирования</h1>

    <div class="row">
        <div class="col-md-4">
            <div class="card mb-4">
                <div class="card-header">
                    Добавить клиента
                </div>
                <div class="card-body">
                    <form method="post" action="<%= request.getContextPath() %>/">
                        <input type="hidden" name="action" value="addClient">
                        <div class="mb-3">
                            <label class="form-label">Имя</label>
                            <input type="text" name="name" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Телефон</label>
                            <input type="text" name="phone" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Добавить клиента</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="card mb-4">
                <div class="card-header">
                    Клиенты
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive mb-0">
                        <table class="table table-striped table-hover mb-0">
                            <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Имя</th>
                                <th>Телефон</th>
                                <th>Email</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                List<Client> clients = (List<Client>) request.getAttribute("clients");
                                if (clients != null && !clients.isEmpty()) {
                                    for (Client c : clients) {
                            %>
                            <tr>
                                <td><%= c.getId() %></td>
                                <td><%= c.getName() %></td>
                                <td><%= c.getPhone() %></td>
                                <td><%= c.getEmail() %></td>
                                <td>
                                    <form method="post" action="<%= request.getContextPath() %>/">
                                        <input type="hidden" name="action" value="deleteClient">
                                        <input type="hidden" name="id" value="<%= c.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-danger">
                                            Удалить
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="4" class="text-center text-muted">Клиентов пока нет</td>
                            </tr>
                            <%
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-header">
            Добавить бронирование
        </div>
        <div class="card-body">
            <form method="post" action="<%= request.getContextPath() %>/">
                <input type="hidden" name="action" value="addBooking">
                <div class="row g-3">
                    <div class="col-md-3">
                        <label class="form-label">Клиент</label>
                        <select name="clientId" class="form-select" required>
                            <%
                                if (clients != null) {
                                    for (Client c : clients) {
                            %>
                            <option value="<%= c.getId() %>"><%= c.getName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Заезд</label>
                        <input type="date" name="checkIn" class="form-control" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Выезд</label>
                        <input type="date" name="checkOut" class="form-control" required>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label">Комната</label>
                        <input type="text" name="roomNumber" class="form-control" required>
                    </div>
                    <div class="col-md-3 d-flex align-items-end">
                        <button type="submit" class="btn btn-success w-100">Добавить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            Бронирования
        </div>
        <div class="card-body p-0">
            <div class="table-responsive mb-0">
                <table class="table table-striped table-hover mb-0">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Клиент</th>
                        <th>Заезд</th>
                        <th>Выезд</th>
                        <th>Комната</th>
                        <th>Статус</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
                        if (bookings != null && !bookings.isEmpty()) {
                            for (Booking b : bookings) {
                    %>
                    <tr>
                        <td><%= b.getId() %></td>
                        <td><%= b.getClient() != null ? b.getClient().getName() : "-" %></td>
                        <td><%= b.getCheckIn() %></td>
                        <td><%= b.getCheckOut() %></td>
                        <td><%= b.getRoomNumber() %></td>
                        <td><%= b.getStatus() %></td>
                        <td>
                            <form method="post" action="<%= request.getContextPath() %>/" class="row g-1">
                                <input type="hidden" name="action" value="editBooking">
                                <input type="hidden" name="id" value="<%= b.getId() %>">
                                <div class="col-md-3">
                                    <input type="date" name="checkIn" class="form-control form-control-sm"
                                           value="<%= b.getCheckIn() %>">
                                </div>
                                <div class="col-md-3">
                                    <input type="date" name="checkOut" class="form-control form-control-sm"
                                           value="<%= b.getCheckOut() %>">
                                </div>
                                <div class="col-md-2">
                                    <input type="text" name="roomNumber" class="form-control form-control-sm"
                                           value="<%= b.getRoomNumber() %>">
                                </div>
                                <div class="col-md-2">
                                    <select name="status" class="form-select form-select-sm">
                                        <%
                                            for (BookingStatus st : BookingStatus.values()) {
                                                String selected = st == b.getStatus() ? "selected" : "";
                                        %>
                                        <option value="<%= st.name() %>" <%= selected %>><%= st.name() %></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-md-2">
                                    <button type="submit" class="btn btn-sm btn-primary w-100">Изменить</button>
                                </div>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center text-muted">Бронирований пока нет</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
</body>
</html>
