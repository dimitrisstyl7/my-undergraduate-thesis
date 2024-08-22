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
            $("#edit-title").val(info.event.title);
            $("#edit-description").val(info.event.extendedProps.description);
            $("#edit-datetime").val(info.event.extendedProps.formattedScheduledDateTime);
            $("#edit-client-fullname").val(info.event.extendedProps.clientFullName);
            $("#edit-modal").modal("toggle");
        }
    }).render();
});


async function fetchAppointments() {
    const response = await fetch('/api/v1/appointments');
    const result = await handleFetchAppointmentsResponse(response);
    if (result?.error) alert(result.error);
    else return result;
}

async function handleFetchAppointmentsResponse(response) {
    if (!response.ok) {
        const message = "Something went wrong while fetching the appointments. " +
            "Please try again. If the problem persists, please contact our Support.";
        return {error: message};
    } else return await response.json();
}