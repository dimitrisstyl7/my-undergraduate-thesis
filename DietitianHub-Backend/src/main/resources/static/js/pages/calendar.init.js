"use strict";

$(document).ready(function () {
    const editModal = $("#edit-modal");
    const createModal = $("#create-modal");
    const confirmCancellationButton = $("#confirm-cancellation-button");
    const cancelModalTitle = $("#cancel-modal-title");
    const editCancelButton = $("#edit-cancel-button");

    // Create a new FullCalendar instance
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
            const event = info.event;
            const title = event.title;
            const description = event.extendedProps.description;
            const formattedAppointmentDateTime = event.extendedProps.formattedAppointmentDateTime;
            const clientFullName = event.extendedProps.clientFullName;

            // Set form values
            $("#edit-title").val(title);
            $("#edit-description").val(description);
            $("#edit-datetime").val(formattedAppointmentDateTime);
            $("#edit-client-fullname").val(clientFullName);

            // Attach click event handler for the save button
            $("#edit-save-button").off("click").on("click", () => updateAppointment(event));

            if (event.start > new Date()) {
                // If the appointment is in the future, allow the user to cancel it
                // Set the text of the cancel button to 'Cancel Appointment'
                editCancelButton.text("Cancel Appointment");

                // Change the cancel button color to danger
                editCancelButton.removeClass("btn-primary").addClass("btn-danger");

                // Enable the cancel button
                editCancelButton.prop("disabled", false);

                // Set the data-bs-toggle and data-bs-target attributes to show the cancel modal
                editCancelButton.attr("data-bs-toggle", "modal");
                editCancelButton.attr("data-bs-target", "#cancel-modal");

                // Attach click event handler for the cancel button
                editCancelButton.off("click").on("click", () => {
                    // Set cancel modal title
                    cancelModalTitle.text(`${title} (${formattedAppointmentDateTime})`);
                });

                // Attach click event handler for the confirm cancellation button
                confirmCancellationButton.off("click").on("click", () => cancelAppointment(event.id));
            } else if (event.start < new Date() && event.extendedProps.status === "SCHEDULED") {
                // If the appointment is in the past, and the status is 'SCHEDULED', allow the user to mark it as completed
                // Set the text of the cancel button to 'Mark as Completed'
                editCancelButton.text("Mark as Completed");

                // Change the cancel button color to green
                editCancelButton.removeClass("btn-danger").addClass("btn-primary");

                // Enable the cancel button
                editCancelButton.prop("disabled", false);

                // Remove the data-bs-toggle and data-bs-target attributes
                editCancelButton.removeAttr("data-bs-toggle");
                editCancelButton.removeAttr("data-bs-target");

                // Attach click event handler for the cancel button (now used to mark the appointment as completed)
                editCancelButton.off("click").on("click", () => completeAppointment(event.id));
            } else {
                // Set the text of the cancel button to 'Completed'
                editCancelButton.text("Completed");

                // Change the cancel button color to primary.
                editCancelButton.removeClass("btn-danger").addClass("btn-primary");

                // Disable the cancel button
                editCancelButton.prop("disabled", true);
            }

            // Show edit modal
            editModal.modal("toggle");
        },
        dateClick: function (info) {
            // Set the date to the selected date, and an arbitrary time
            $("#create-datetime").val(`${info.dateStr}T12:00`);

            // Show create modal.
            createModal.modal("toggle");
        },
        eventTimeFormat: {
            hour: "2-digit",
            minute: "2-digit",
            meridiem: true
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

    // Set on click event handler for the creation submit button.
    $("#create-submit-button").off("click").on("click", () => createAppointment());

    // Select all elements with IDs that start with "todays-appointment-" .
    // Iterate over each row and attach click event handlers for the complete and cancel buttons.
    let rows = $("[id^='todays-appointment-']");
    rows.each((index, row) => {
        const appointmentId = $(row).find('input[type="hidden"]').val();
        const completeButton = $(row).find(`#complete-appointment-${appointmentId}-button`);
        const cancelButton = $(row).find(`#cancel-appointment-${appointmentId}-button`);

        // Attach click event handler for the complete button.
        completeButton.off("click").on("click", () => completeAppointment(appointmentId));

        // Attach click event handler for the decline button.
        cancelButton.off("click").on("click", () => {
            // Set cancel modal title.
            cancelModalTitle.text(`Cancel Appointment #${index + 1}`);

            // Attach click event handler for the confirm cancellation button.
            confirmCancellationButton.off("click").on("click", () => cancelAppointment(appointmentId));
        });
    });

    // Select all elements with IDs that start with "pending-appointment-" .
    // Iterate over each row and attach click event handlers for the approval and decline buttons.
    rows = $("[id^='pending-appointment-']");
    rows.each((index, row) => {
        const appointmentId = $(row).find('input[type="hidden"]').val();
        const approvalButton = $(row).find(`#approve-appointment-${appointmentId}-button`);
        const declineButton = $(row).find(`#decline-appointment-${appointmentId}-button`);
        const confirmDeclineButton = $(`#confirm-decline-button`);

        // Attach click event handler for the approval button.
        approvalButton.off("click").on("click", () => approveAppointment(appointmentId));

        // Attach click event handler for the decline button.
        declineButton.off("click").on("click", () => {
            // Set decline modal title.
            $("#decline-modal-title").text(`Decline Appointment #${index + 1}`);

            // Attach click event handler for the confirm decline button.
            confirmDeclineButton.off("click").on("click", () => declineAppointment(appointmentId));
        });
    });

    // Render the calendar.
    calendar.render();
});
