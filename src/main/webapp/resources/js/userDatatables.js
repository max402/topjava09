var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    init();
});

function toggleUser(checkbox) {
    var enabled = checkbox.is(":checked");
    checkbox.parent().parent().css('text-decoration', enabled ? "none" : "line-through")
    $.ajax({
        type: "POST",
        url: ajaxUrl + checkbox.parent().parent().prop('id'),
        data: 'enabled='+ enabled,
        success: function () {
            successNoty(enabled ? 'enabled' : 'disabled');
        }
    });
}