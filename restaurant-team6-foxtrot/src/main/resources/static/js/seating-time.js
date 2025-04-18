function toggleDeleteButtons(e, id) {
    e.preventDefault();

    document.getElementById('toggle-buttons-' + id).style.display = 'none';
    document.getElementById('delete-buttons-' + id).style.display = 'inline-block';

}

function cancelDelete(e, id) {
    e.preventDefault();

    document.getElementById('toggle-buttons-' + id).style.display = 'inline-block';
    document.getElementById('delete-buttons-' + id).style.display = 'none';

}
