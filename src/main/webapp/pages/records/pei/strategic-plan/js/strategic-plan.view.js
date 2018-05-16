var StrategicPlan = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        paginateResult: function () {
            $('#result-table').dataTable({
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [1]
                    }],
                "order": [
                    [0, 'asc']
                ],
                "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                        "t" +
                        "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
                "autoWidth": true,
                "oLanguage": {
                    "sLengthMenu": "_MENU_",
                    "sSearch": "<div class=\"input-group\">_INPUT_<span class=\"input-group-addon\"><i class=\"fa fa-search\"></i></span></div>",
                    "sInfo": "Mostrando <span class='txt-color-darken'>_START_</span> a <span class='txt-color-darken'>_END_</span> de <span class='text-primary'>_TOTAL_</span> registros",
                    "oPaginate": {
                        "sPrevious": "<i class=\"fa fa-arrow-left\"/>",
                        "sNext": "<i class=\"fa fa-arrow-right\"/>"
                    },
                    "sInfoEmpty": "",
                    "sInfoFiltered": " - buscado desde _MAX_ registro(s)",
                    "sZeroRecords": "No se han encontrado planes estrategicos"
                }
            });
        },
        searcherConfigure: function () {
            $("#year").on("keypress", function (event) {
                if (event.charCode == 13) {
                    FundingSource.search();
                    return false;
                }
            }).select2({
                placeholder: 'Cualquier año',
                width: '100%',
                maximunSelectionSize: 1
            })
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#year").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:year").val($("#year").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#year").removeAttr("disabled");
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
        }
    };
}();
$(function () {
    StrategicPlan.searcherConfigure();
});
