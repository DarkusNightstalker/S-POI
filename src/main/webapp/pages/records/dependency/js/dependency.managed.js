/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Dependency = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        configurePath: function () {

            $("#form\\:path").keydown(function (e) {
                if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
                        (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
                        (e.keyCode >= 35 && e.keyCode <= 40)) {
                    return;
                }
                if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
                    e.preventDefault();
                }
            });
        },
        paginateABPSearch: function () {
            $('#form-abp table').dataTable({
                "sDom": "f" +
                        "t",
                "aoColumnDefs": [
                    {
                        "bSortable": false,
                        "aTargets": [0]
                    }],
                "order": [
                    [1, 'asc']
                ],
                "autoWidth": true,
                "bPaginate": false,
                "oLanguage": {
                    "sLengthMenu": "_MENU_",
                    "sSearch": "<div class=\"search-dt-input hidden\">_INPUT_</div>",
                    "sInfo": "Mostrando <span class='txt-color-darken'>_START_</span> a <span class='txt-color-darken'>_END_</span> de <span class='text-primary'>_TOTAL_</span> registros",
                    "oPaginate": {
                        "sPrevious": "<i class=\"fa fa-arrow-left\"/>",
                        "sNext": "<i class=\"fa fa-arrow-right\"/>"
                    },
                    "sInfoEmpty": "",
                    "sInfoFiltered": " - buscado desde _MAX_ registro(s)",
                    "sZeroRecords": "No se han encontrado registros"
                }
            });
        },
        disableContentForPath: function () {
            $("#form\\:name").attr("disabled", "disabled");
            $("#form\\:operational").attr("disabled", "disabled");
            $("#form-bc").attr("display", "none");
            $("#form-config\\:strategic-axis").attr("disabled", "disabled");
            $("#form-config\\:specific-goal").attr("disabled", "disabled");
            $('#form-bc input[type="text"]').attr("disabled", "disabled");
            $("#form\\:parents").closest(".form-group").addClass("hidden");
        },
        validatePath: function () {
            if ($("#form\\:path").val() != "") {
                $("#form\\:path").attr("readonly", "readonly");
                $("#form\\:path").parent().find("i").removeClass("hidden");
                Dependency.disableContentForPath();
                validatePath();
            } else {
                $.smallBox({
                    title: "Codigo no valido",
                    content: 'El codigo no puede estar vacio!!',
                    color: "#C46A69",
                    timeout: 5000,
                    icon: "fa fa-warning shake animated"
                });
            }
        },
        endValidatePath: function () {
            $("#form\\:path").parent().find("i").addClass("hidden");
            $("#form\\:path").removeAttr("readonly");
            if ($("#form\\:valid-path").val() == "true") {
                $("#form-config\\:specific-goal").removeAttr("disabled");
                $("#form-bc").attr("display", "block");
                $("#form\\:operational").removeAttr("disabled");
                $("#form-config\\:strategic-axis").removeAttr("disabled");
                $('#form-bc input[type="text"]').removeAttr("disabled");
                $("#form\\:name").removeAttr("disabled");
                $("#form\\:parents").closest(".form-group").removeClass("hidden");
                $("#form\\:name").focus();
            } else {
                $("#form\\:path").focus();
            }
        },
        save: function () {
            if ($("#form\\:name").attr("disabled") == "disabled") {
                $("#form\\:path").val("");
            }
            if ($("#form").valid()) {
                if (document.getElementById("form:operational").checked) {
                    submitBC();
                } else {
                    save();
                }
            }
        },
        back: function () {
            if ($("#form\\:name").attr("disabled") == "disabled") {
                $("#form\\:path").val("");
            }
            if ($("#form\\:path").val() != "") {
                $.SmartMessageBox({
                    title: "Aviso!",
                    content: "Esta seguro de salir? (Se perderan todos los cambios realizados)",
                    buttons: '[No][Si]'
                }, function (ButtonPressed) {
                    if (ButtonPressed === "Si") {
                        $("#form\\:back").trigger("click");
                    }
                });
            } else {
                $("#form\\:back").trigger("click");
            }
        },
        configureBC: function () {
            $("#form-bc table input").on("blur", function () {
                try {
                    var value = parseFloat(this.value);
                    var header_y = "header-" + $(this).attr("data-y");
                    var header_x = "header-" + $(this).attr("data-x");
                    $("#form-bc table td[data-y=" + header_y + "]").css("background", "#ffffff");
                    $("#form-bc table theader th[data-x=" + header_x + "]").css("background", "#ffffff");
                    if (value == 0)
                        this.value = "";
                } catch (Exception) {
                    this.value = "";
                }
            }).on("input", function () {
                Dependency.updateTotalBC(this);
            }).on("focus", function () {
                var header_y = "header-" + $(this).attr("data-y");
                var header_x = "header-" + $(this).attr("data-x");
                $("#form-bc table td[data-y=" + header_y + "]").css("background", "#ecf3f8");
                $("#form-bc table theader th[data-x=" + header_x + "]").css("background", "none");
            }).trigger("input").trigger("blur")

        },
        updateTotalBC: function (input) {
            var fs = $(input).attr("data-x");
            var total = 0;
            $("#form-bc input[data-x='" + fs + "']").each(function () {
                var current_value;
                try {
                    current_value = parseFloat(this.value);
                } catch (Exception) {
                    current_value = 0;
                }
                if (isNaN(current_value))
                    current_value = 0;
                total += current_value;
            });
            $("#form-bc\\:total-" + fs).html("S/. " + total);
        }
    };
}();

$(function () {
    $("#form\\:path").on("keypress", function (event) {
        if (event.charCode == 13) {
            if ($("#form\\:name").attr("disabled") == "disabled") {
                Dependency.validatePath();
            } else {
                Dependency.save();
            }
            return false;
        }
    }).on("input", function () {
        Dependency.disableContentForPath();
    }).focus();
    $("#form\\:name").on("keypress", function (event) {
        if (event.charCode == 13) {
            Dependency.save();
            return false;
        }
    })
    if ($("#form\\:path").val() == "" || $("#form\\:valid-path").val() == "false") {
        Dependency.disableContentForPath();
    }
    $("#form").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            "form:name": {
                required: true
            },
            "form:path": {
                required: true
            }
        },
        messages: {
            "form:name": {
                required: 'Por favor aingrese un nombre'
            },
            "form:path": {
                required: 'Por favor ingrese un codigo'
            }
        },
        /* @validation highlighting + error placement  
         ---------------------------------------------------- */

        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
        },
        errorPlacement: function (error, element) {
            element.parent().remove(".has-error");
            error.insertAfter(element);
        }

    });
})

