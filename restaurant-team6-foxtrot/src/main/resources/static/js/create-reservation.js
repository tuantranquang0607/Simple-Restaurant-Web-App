function updateSeatings() {
    console.log(seatingData);
    const eventId = document.getElementById("event").value;
    const seatingSelect = document.getElementById("seating");
    const noSeatingMessage = document.getElementById("no-seating-message");

    seatingSelect.innerHTML = '<option value="" disabled selected>-- Select Seating --</option>';

    const filteredSeatings = seatingData.filter(seating => seating.eventId == eventId);

    if (filteredSeatings.length > 0) {
        noSeatingMessage.style.display = "none";
        seatingSelect.style.display = "block";

        filteredSeatings.forEach(seating => {
            const option = document.createElement("option");
            option.value = seating.id;
            option.textContent = seating.name;
            seatingSelect.appendChild(option);
        });
    } else {
        seatingSelect.style.display = "none";
        noSeatingMessage.style.display = "block";
    }
}
function showStatusConfirmation(e, id, newStatus) {
    e.preventDefault();

    const currentStatus = document.querySelector(`#status-buttons-${id}`).getAttribute('data-current-status');
    if (currentStatus === newStatus) return;

    if (currentStatus === 'APPROVED') {
        alert("This reservation has already been approved and cannot be changed.");
        return;
    }

    document.getElementById('status-buttons-' + id).style.display = 'none';

    const confirmGroup = document.getElementById('confirm-buttons-' + id);
    confirmGroup.style.display = 'inline-block';

    const form = confirmGroup.closest('form') || confirmGroup;
    const statusInput = form.querySelector('.status-input');
    statusInput.value = newStatus;

    const confirmBtn = form.querySelector('button[data-status]');
    confirmBtn.setAttribute('data-status', newStatus);
    confirmBtn.textContent = `Change to "${newStatus.toUpperCase()}"`;

    const tableDropdown = form.querySelector('.table-dropdown');
    const tableSelect = form.querySelector('select[name="tableId"]');

    if (newStatus === 'APPROVED') {
        tableDropdown.style.display = 'block';
        confirmBtn.disabled = !tableSelect.value; // Disable unless a table is selected
    } else {
        tableDropdown.style.display = 'none';
        confirmBtn.disabled = false;
    }
}

function cancelStatusChange(e, id) {
    e.preventDefault();
    document.getElementById('status-buttons-' + id).style.display = 'inline-block';
    document.getElementById('confirm-buttons-' + id).style.display = 'none';
}

function onTableSelectChange(reservationId) {
    const form = document.getElementById('confirm-buttons-' + reservationId);
    const tableSelect = form.querySelector('select[name="tableId"]');
    const confirmBtn = form.querySelector('button[data-status]');

    confirmBtn.disabled = !tableSelect.value;
}

