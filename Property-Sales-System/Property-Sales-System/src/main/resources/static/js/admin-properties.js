<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin Properties</title>
</head>
<body>
<h1>Properties</h1>

<!-- Add / Update Form -->
<form th:action="@{/admin/properties/add}" th:object="${property}" method="post">
    <input type="text" th:field="*{title}" placeholder="Title" required>
    <input type="text" th:field="*{owner}" placeholder="Owner" required>
    <input type="text" th:field="*{status}" placeholder="Status" required>
    <button type="submit">Save</button>
</form>

<hr>

<!-- Properties Table -->
<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Owner</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="prop : ${properties}">
            <td th:text="${prop.id}"></td>
            <td th:text="${prop.title}"></td>
            <td th:text="${prop.owner}"></td>
            <td th:text="${prop.status}"></td>
            <td>
                <a th:href="@{/admin/properties/delete/{id}(id=${prop.id})}">Delete</a>
                <!-- Optional: add Update form or modal -->
            </td>
        </tr>
    </tbody>
</table>
</body>
</html>
