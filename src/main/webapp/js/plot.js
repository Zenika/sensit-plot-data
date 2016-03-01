function plotMotion(data) {
  var chart = new CanvasJS.Chart("motionData", {
    zoomEnabled: true,
    axisX: {
      title: 'Heure',
      valueFormatString: "DD/MM HH:mm",
      labelAngle: -20,
      viewportMaximum: new Date(),
      viewportMinimum: new Date().setHours(4,0,0,0)
    },
    data: [
      {
        type: "line",

        dataPoints: data.map(function(p) {
          var datax = new Date(p.date);
          var datay = (p.data.indexOf(':') > -1) ? p.data.split(":")[1] : p.data;

          return {
            x: datax,
            y: parseInt(datay)
          };
        })
      }
    ]
  });

  chart.render();
}

function plotTemperature(data) {
  var chart = new CanvasJS.Chart("temperatureData", {
    zoomEnabled: true,
    axisX: {
      title: 'Heure',
      valueFormatString: "DD/MM HH:mm",
      labelAngle: -20,
      viewportMaximum: new Date(),
      viewportMinimum: new Date().setHours(4,0,0,0)
    },
    axisY: {
      includeZero: false
    },
    data: [
      {
        type: "spline",

        dataPoints: data
          .filter(function (p) {
            return p.data.indexOf(':') == -1
          })
          .map(function(p) {
            return {
              x: new Date(p.date),
              y: parseFloat(p.data)
            };
          })
      }
    ]
  });

  chart.render();
}

$(function() {
  $.get({
    url: "/api/sensors/motion"
  })
  .done(function(response) {
    plotMotion(response.content);
  });

  $.get({
    url: "/api/sensors/temperature"
  })
  .done(function(response) {
    plotTemperature(response.content);
  });
});
