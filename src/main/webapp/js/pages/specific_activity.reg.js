var SpecificActivity = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        paginateResult: function () {
            $('#result-table').dataTable({
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [3]
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
                    "sZeroRecords": "No se han encontrado ejes estrategicos"
                }
            });
        },
        searcherConfigure: function () {
            $("#code").on("keypress", function (event) {
                if (event.charCode == 13) {
                    SpecificActivity.search();
                    return false;
                }
            })
            $("#name").on("keypress", function (event) {
                if (event.charCode == 13) {
                    SpecificActivity.search();
                    return false;
                }
            })
            $("#strategic-axis").select2({
                placeholder: 'Cualquier eje estratégico',
                width: '100%',
                maximunSelectionSize: 1
            })
        },
        beginSearch: function () {
            $("#btn-search i").css("display", "block");
            $("#btn-search").attr("disabled", "disabled");
            $("#code").attr("disabled", "disabled");
            $("#name").attr("disabled", "disabled");
            $("#strategic-axis").attr("disabled", "disabled");
        },
        search: function () {
            $("#form-search\\:code").val($("#code").val());
            $("#form-search\\:name").val($("#name").val());
            $("#form-search\\:strategic-axis").val($("#strategic-axis").val());
            search();
        },
        endSearch: function () {
            $("#btn-search i").css("display", "none");
            $("#btn-search").removeAttr("disabled");
            $("#code").removeAttr("disabled");
            $("#name").removeAttr("disabled");
            $("#strategic-axis").removeAttr("disabled");
            var message = $("#message-search").val();
            if (message.length != 0) {

            }
        },
        updateProgress: function () {
            $("#import-state").html($("#form-progress\\:state").val());
            $("#import-percent").html($("#form-progress\\:percent").val());
            $("#form-import .progress-bar").css("width" , $("#form-progress\\:percent").val()+"%");
            $("#form-import\\:load-wrapper table tbody").append($("#form-progress\\:content").val());
            var objDiv = document.getElementById("import-data");
            objDiv.scrollTop = objDiv.scrollHeight;
            if ($("#form-progress\\:finalize").val() != "true") {
                updateProgress();
            } else {
                $("#code").val('');
                $("#name").val('');
                $("#form-search\\:code").val($("#code").val());
                $("#form-search\\:name").val($("#name").val());
                search();
            }
        },
        updateProgressABP: function () {
            $("#import-state-abp").html($("#form-progress-abp\\:state").val());
            $("#import-percent-abp").html($("#form-progress-abp\\:percent").val());
            $("#form-import-abp .progress-bar").css("width" , $("#form-progress-abp\\:percent").val()+"%");
            $("#form-import-abp\\:load-wrapper table tbody").append($("#form-progress-abp\\:content").val());
            var objDiv = document.getElementById("import-data-abp");
            objDiv.scrollTop = objDiv.scrollHeight;
            if ($("#form-progress-abp\\:finalize").val() != "true") {
                updateProgress();
            } else {
                $("#code").val('');
                $("#name").val('');
                $("#form-search\\:code").val($("#code").val());
                $("#form-search\\:name").val($("#name").val());
                search();
            }
        }
    };
}();
$(function () {
    SpecificActivity.searcherConfigure();
});
