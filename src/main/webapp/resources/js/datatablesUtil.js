var form;

function makeEditable() {
    form = $('#detailsForm');

    $('#dateTime').datetimepicker({
        format:'Y-m-d H:i'
    });

    $('#startDate, #endDate').datetimepicker({
        timepicker: false,
        format:'Y-m-d'
    });

    $('#startTime, #endTime').datetimepicker({
        datepicker: false,
        format:'H:i'
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add(title) {
    form.find("input[name='id']").val("");
    $('#modalTitle').html(title);
    // form.find(":input").val("");
    // form.trigger('reset');
    form[0].reset();
    $('#editRow').modal();
}

function updateRow(id) {
    $('#modalTitle').html($('#editTitle').attr("value"));
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(key === "dateTime" ? value.replace('T', ' ').substr(0, 16) : value);
        });
        $('#editRow').modal();
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('common.deleted');
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function save() {
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('common.saved');
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    noty({
        text: i18n[key],
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: i18n['common.failed'] + ': ' + jqXHR.statusText + "<br>" + jqXHR.responseJSON,
        type: 'error',
        layout: 'bottomRight'
    });
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-primary" onclick="updateRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-xs btn-danger" onclick="deleteRow(' + row.id + ');">'+
            '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>';
    }
}
