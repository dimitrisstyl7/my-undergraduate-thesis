$(document).ready(function () {
    new FullCalendar.Calendar($('#calendar')[0], {
        themeSystem: "bootstrap",
        headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: "dayGridMonth,listMonth"
        },
        buttonText: {
            today: "Today",
            month: "Month",
            list: "List",
            prev: "Prev",
            next: "Next"
        },
        initialView: "dayGridMonth",
        selectable: true,
        eventClassNames: "custom-event",
        events: () => fetchAppointments(),
        eventClick: function (info) {
            const editModal = $("#edit-modal");

            // Set form values.
            $("#edit-title").val(info.event.title);
            $("#edit-description").val(info.event.extendedProps.description);
            $("#edit-datetime").val(info.event.extendedProps.formattedScheduledDateTime);
            $("#edit-client-fullname").val(info.event.extendedProps.clientFullName);

            // Attach click event handler for the save button.
            $("#edit-save-button").off("click").on("click", () => updateAppointment(info.event));

            // Show the modal.
            editModal.modal("toggle");

            // Always unbind and rebind the hidden event handler.
            editModal.off('hidden.bs.modal').on('hidden.bs.modal', () => resetEditModal());
        },
    }).render();
});
