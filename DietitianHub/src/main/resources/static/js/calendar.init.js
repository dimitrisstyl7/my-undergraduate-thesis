$(document).ready(function () {
    const editModal = $("#edit-modal");
    const createModal = $("#create-modal");

    // Create a new FullCalendar instance.
    const calendar = new FullCalendar.Calendar($('#calendar')[0], {
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
            const title = info.event.title;
            const description = info.event.extendedProps.description;
            const formattedScheduledDateTime = info.event.extendedProps.formattedScheduledDateTime;
            const clientFullName = info.event.extendedProps.clientFullName;

            // Set form values.
            $("#edit-title").val(title);
            $("#edit-description").val(description);
            $("#edit-datetime").val(formattedScheduledDateTime);
            $("#edit-client-fullname").val(clientFullName);

            // Attach click event handler for the save button.
            $("#edit-save-button").off("click").on("click", () => updateAppointment(info.event));

            // Attach click event handler for the delete button.
            $("#edit-delete-button").off("click").on("click", () => {
                // Set delete modal title.
                $("#delete-modal-title").text(`${title} (${formattedScheduledDateTime})`);
            });

            // Attach click event handler for the confirm deletion button.
            $("#confirm-deletion-button").off("click").on("click", () => deleteAppointment(info.event));

            // Show edit modal.
            editModal.modal("toggle");
        },
        dateClick: function (info) {
            // Set the date to the selected date, and an arbitrary time.
            $("#create-datetime").val(`${info.dateStr}T12:00`);

            // Show create modal.
            createModal.modal("toggle");
        }
    });

    // Attach click event handler for the edit reset button.
    $("#edit-reset-button").off("click").on("click", () => resetEditModal());

    // On edit modal close, clear any error messages.
    editModal.off("hidden.bs.modal").on("hidden.bs.modal", () => clearEditErrorMessages());

    // On create modal close, reset the form and clear any error messages.
    createModal.off("hidden.bs.modal").on("hidden.bs.modal", () => {
        $("#create-form")[0].reset();
        clearCreateErrorMessages();
    });

    // Set on click event handler for the create submit button.
    $("#create-submit-button").off("click").on("click", () => createAppointment(calendar));

    // Render the calendar.
    calendar.render();
});
