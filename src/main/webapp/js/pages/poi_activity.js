var POIActivitySearch = function () {
    return {
        configDependency: function () {
            $("#form-record\\:dependency").select2({
                width: "100%",
                placeholder: "Seleccione una dependencia"
            })
        },
        configABP: function () {
            $("#form-record\\:activity-budge-program").select2({
                width: "100%",
                placeholder: "Seleccione una actividad presupuestal"
            })
        },
        configSpecificActivity: function () {
            $("#form-record\\:specific-activity").select2({
                width: "100%",
                placeholder: "Seleccione una actividad especifica"
            })
        }
    };
}();
var POIActivityManaged = function () {
    return {
        save: function () {
            if ($("#fform").valid()) {
                $("#form\\:name").val($("#name").val());
                $("#form\\:code").val($("#code").val());
                save();
            }
        },
        begin: function () {
            $("#form-record\\:specific-activity").prop("disabled", true);
            $("#form-record\\:dependency").prop("disabled", true);
            $("#form-record\\:activity-budge-program").prop("disabled", true);
            $("#form-record\\:name").prop("disabled", true);
            $("#records a.btn").css("display", "none");
        },
        end: function () {
            $("#form-record\\:specific-activity").prop("disabled", false);
            $("#form-record\\:dependency").prop("disabled", false);
            $("#form-record\\:activity-budge-program").prop("disabled", false);
            $("#form-record\\:name").prop("disabled", false);
            $("#table-poi form a").prop("disabled", false);
            $("#records a.btn").css("display", "block");
        },
        slide: function () {
            $('#managed .jarviswidget>div:first').slideToggle('fast');
            var i = $("#managed\\:slide").find('i');
            if (i.hasClass("fa-plus-circle")) {
                i.addClass('fa-minus-circle');
                i.removeClass('fa-plus-circle')
            } else {
                i.addClass('fa-plus-circle');
                i.removeClass('fa-minus-circle')
            }
        }
    };
}();