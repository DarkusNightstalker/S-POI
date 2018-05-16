/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Classifier = function () {
    
    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        disableContentForPath: function () {
            $("#name").attr("disabled", "disabled");
            $("#description").attr("disabled", "disabled");
            $("#parents").closest(".form-group").addClass("hidden");

        },
        validatePath: function () {
            if ($("#fform-path").valid()) {
                $("#form\\:path").val($("#path").val());
                $("#path").attr("disabled", "disabled");
                $("#path").parent().find("i").removeClass("hidden");
                Classifier.disableContentForPath();
                validatePath();
            }
        },
        endValidatePath: function () {
            $("#path").parent().find("i").addClass("hidden");
            $("#path").removeAttr("disabled");
            if ($("#form\\:path-valid").val() == "true") {
                $("#name").removeAttr("disabled");
                $("#description").removeAttr("disabled");
                $("#parents").closest(".form-group").removeClass("hidden");
                $("#name").focus();
            } else {
                $("#path").focus();
                $.smallBox({
                    title: "Codigo no valido",
                    content: $("#form\\:error-message").val(),
                    color: "#C46A69",
                    timeout: 5000,
                    icon: "fa fa-warning shake animated"
                });
            }
        },
        save: function () {
            if ($("#name").attr("disabled") == "disabled") {
                $("#path").val("");
            }
            if ($("#fform-path").valid() && $("#fform-other").valid()) {
                $("#form\\:name").val($("#name").val());
                $("#form\\:description").val($("#description").val());
                save();
            }
        },
        back: function () {
            if ($("#name").attr("disabled") == "disabled") {
                $("#path").val("");
            }
            if ($("#path").val() != "") {
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
        }
    };
}();

$(function () {
    $("#path").on("keypress", function (event) {
        if (event.charCode == 13) {
            Classifier.validatePath();
            return false;
        }
    }).on("input", function () {
        Classifier.disableContentForPath();
    }).focus();
    if ($("#path").val() == "" || $("#form\\:path-valid").val() == "false") {
        Classifier.disableContentForPath();
    }
    $("#create-again").on("change", function () {
        $("#form\\:create-again").val(this.checked);
    })
    $("#fform-other").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            name: {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            name: {
                required: 'El nombre no puede ser vacio'
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
    $("#fform-path").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            path: {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            path: {
                required: 'El codigo no puede ser vacio'
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

