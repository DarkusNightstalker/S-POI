var BoxNeeds = function () {
    var numberWithCommas = function (x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    };
    return {
        configureAmount: function () {

            var calculateAmount = function () {
                var amount = 0;
                $(".month-schedule").each(function () {
                    var current = parseFloat(this.value);
                    if (isNaN(current)) {
                        current = 0;
                    }
                    amount = amount + current;
                });

                var uP = parseFloat($("#form-manage\\:unit-price").val());
                if (isNaN(uP))
                    uP = 0;
                var nSaldo = (amount * uP);
                nSaldo = nSaldo.toFixed(2);
                var saldo = "S/. " + numberWithCommas(nSaldo);
                $("#used-balance").removeClass("text-success").removeClass("text-warning").removeClass("text-danger");
                var totalSaldo = parseFloat($("#current-balance").val());
                if (nSaldo > totalSaldo) {
                    $("#used-balance").addClass("text-danger");
                    saldo += " &nbsp;<i class='fa fa-warning bounceIn animated' title='Advertencia! El monto supera a la cantidad disponible' />";
                    $("#used-balance").after();
                    $("#remaining-balance").html("S/. 0.00");
                } else if (nSaldo > (totalSaldo * 0.7)) {
                    $("#used-balance").addClass("text-warning");
                    saldo += " &nbsp;<i class='fa fa-warning bounceIn animated' title='Advertencia! El monto esta cerca de superar el maximo' />";
                    $("#remaining-balance").html("S/. " + numberWithCommas((totalSaldo - nSaldo).toFixed(2)));
                } else {
                    $("#used-balance").addClass("text-success");
                    $("#remaining-balance").html("S/. " + numberWithCommas((totalSaldo - nSaldo).toFixed(2)));
                }
                $("#used-balance").html(saldo);
            };
            $(".month-schedule")
                    .on("input", calculateAmount);
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "") {
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
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "¿ Esta seguro de eliminar el registro de necesidades ?",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    del();
                }
            });
        },
        recovery: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de restaurar la necesidad?",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    recovery();
                }
            });
        },
        end_save: function () {
            $("#btn-manage-save").prop("disabled", false);

            if (!document.getElementById('form-manage:create-again').checked) {
                $('#modal-manage').modal('hide');
            }
        },
        save: function () {
            $("#btn-manage-save").prop("disabled", true);
            var float = parseFloat($("#used-balance").text().replace("S/. ", ""));
            var totalSaldo = parseFloat($("#current-balance").val());
            if (float > totalSaldo) {
                $.smallBox({
                    title: "ERROR",
                    content: "El monto pedido excede el presupuesto disponible",
                    color: "#C79121",
                    timeout: 3000,
                    icon: "fa fa-exclamation shaked animation animated"
                });
                $("#btn-manage-save").prop("disabled", false);
            } else {
                var total = 0;
                $(".month-schedule").each(function () {
                    var val = this.value
                    var valueI;
                    try {
                        valueI = parseFloat(val)
                    } catch (exception) {
                        valueI = 0;
                    }
                    total += valueI;

                })
                if (total == 0) {
                    $.smallBox({
                        title: "ERROR",
                        content: "El pedido mensual se encuentra vacio",
                        color: "#C79121",
                        timeout: 3000,
                        icon: "fa fa-exclamation shaked animation animated"
                    });
                    $("#btn-manage-save").prop("disabled", false);
                } else
                    save();
            }
        }
    };
}();
