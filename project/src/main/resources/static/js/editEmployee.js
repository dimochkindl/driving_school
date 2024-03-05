function enableEdit(employeeId) {
    // Скрываем текстовые поля и показываем поля для редактирования
    document.getElementById('name-' + employeeId).style.display = 'none';
    document.getElementById('edit-name-' + employeeId).style.display = 'inline';

    document.getElementById('surname-' + employeeId).style.display = 'none';
    document.getElementById('edit-surname-' + employeeId).style.display = 'inline';

    document.getElementById('phone-' + employeeId).style.display = 'none';
    document.getElementById('edit-phone-' + employeeId).style.display = 'inline';

    document.getElementById('experience-' + employeeId).style.display = 'none';
    document.getElementById('edit-experience-' + employeeId).style.display = 'inline';

    document.getElementById('post-' + employeeId).style.display = 'none';
    document.getElementById('edit-postId-' + employeeId).style.display = 'inline';

    // Показываем кнопку "Применить изменения"
    document.getElementById('save-btn-' + employeeId).style.display = 'inline';
}

function saveChanges(employeeId) {
    // Дополнительные действия при сохранении изменений
    // Например, отправка данных на сервер

    // Скрываем поля для редактирования и показываем текстовые поля
    document.getElementById('name-' + employeeId).style.display = 'inline';
    document.getElementById('edit-name-' + employeeId).style.display = 'none';

    document.getElementById('surname-' + employeeId).style.display = 'inline';
    document.getElementById('edit-surname-' + employeeId).style.display = 'none';

    document.getElementById('phone-' + employeeId).style.display = 'inline';
    document.getElementById('edit-phone-' + employeeId).style.display = 'none';

    document.getElementById('experience-' + employeeId).style.display = 'inline';
    document.getElementById('edit-experience-' + employeeId).style.display = 'none';

    document.getElementById('post-' + employeeId).style.display = 'inline';
    document.getElementById('edit-postId-' + employeeId).style.display = 'none';

    // Скрываем кнопку "Применить изменения"
    document.getElementById('save-btn-' + employeeId).style.display = 'none';
}
