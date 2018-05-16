/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Home = function () {

    return {
        plot: function (index, data) {
            var months = [
                [1, "Ene"],
                [2, "Feb"],
                [3, "Mar"],
                [4, "Abr"],
                [5, "May"],
                [6, "Jun"],
                [7, "Jul"],
                [8, "Ago"],
                [9, "Sep"],
                [10, "Oct"],
                [11, "Nov"],
                [12, "Dic"]
            ];
//            var data = [], totalPoints = 200, $UpdatingChartColors = $("#updating-chart-" + index).css('color');
            var options = {
                grid: {
                    hoverable: true
                },
                tooltip: true,
                tooltipOpts: {
                    content: "<b>%s</b> Mes: %x, Monto: <span>S/. %y.2</span>",
                    defaultTheme: false
                },
                xaxis: {
                    ticks: months
                }
            };
            var plot = $.plot($("#updating-chart-" + index), data, options);
            $("#updating-chart-" + index).bind("plothover", function (event, pos, item) {
                if (item) {

                    var x = item.series.xaxis.ticks[item.dataIndex];
                    var y = item.datapoint[1];

                    $("#tooltip").html("x is: " + x.label + " --  y is: " + y)
                            .css({
                                top: item.pageY + 5,
                                left: item.pageX + 5
                            })
                            .fadeIn(200);
                } else {
                    $("#tooltip").hide();
                }
            });
        }
    };
}();


