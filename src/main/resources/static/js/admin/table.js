
export const populateUserTable = (users, openUserModal) => {
    const tbody = $('#adminUsersTable tbody');
    tbody.empty();
    users.forEach(user => {
        const row = createUserRow(user, openUserModal);
        tbody.append(row);
    });
};

const createUserRow = (user, openUserModal) => {
    const row = $(`<tr></tr>`).attr('data-id', user.id);
    const columns = Object.entries(user)
        .filter(([key]) => key !== 'password')
        .map(([key, value]) => {
            if (key === 'roles') {
                // Если ключ 'roles', обрезаем 'ROLE_' и объединяем их в строку
                const rolesWithoutPrefix = value.map(role => role.replace('ROLE_', '')).join(', ');
                return $('<td></td>').text(rolesWithoutPrefix);
            } else {
                return $('<td></td>').text(value);
            }
        });

    const editButton = createButton('Edit', 'btn-info', () => openUserModal(user, 'edit'));
    const deleteButton = createButton('Delete', 'btn-danger', () => openUserModal(user, 'delete'));

    columns.push($('<td></td>').append(editButton));
    columns.push($('<td></td>').append(deleteButton));
    row.append(columns);
    return row;
};

const createButton = (text, className, onClick) => {
    const button = $(`<button type="button" class="btn ${className}">${text}</button>`);
    button.on('click', onClick);
    return button;
};