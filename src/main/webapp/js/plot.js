(function() {
  var nextMotionDataLink = '';
  var nextTemperatureDataLink = '';
  var sensitMotionChart = null;
  var sensitTemperatureChart = null;

  function formatMotionData(data) {
    return data.map(function(measure) {
      var datax = new Date(measure.date);
      var datay = (measure.data.indexOf(':') > -1) ? measure.data.split(":")[1] : measure.data;

      return {
        x: datax,
        y: parseInt(datay)
      };
    });
  }

  function plotMotion(data) {
    sensitMotionChart = new CanvasJS.Chart("motionData", {
      zoomEnabled: true,
      axisX: {
        title: 'Heure',
        valueFormatString: "DD/MM HH:mm",
        labelAngle: -20,
      },
      data: [
        {
          type: "line",

          dataPoints: formatMotionData(data)
        }
      ]
    });

    sensitMotionChart.render();
  }

  function formatTemperatureData(data) {
    return data
      .filter(function (p) {
        return p.data.indexOf(':') == -1
      })
      .map(function(p) {
        return {
          x: new Date(p.date),
          y: parseFloat(p.data)
        };
      });
  }

  function plotTemperature(data) {
    sensitTemperatureChart = new CanvasJS.Chart("temperatureData", {
      zoomEnabled: true,
      axisX: {
        title: 'Heure',
        valueFormatString: "DD/MM HH:mm",
        labelAngle: -20
      },
      axisY: {
        includeZero: false
      },
      data: [
        {
          type: "spline",

          dataPoints: formatTemperatureData(data)
        }
      ]
    });

    sensitTemperatureChart.render();
  }

  function loadMoreMotionData() {
    $.get({
      url: nextMotionDataLink
    })
    .done(function(response) {
      formatMotionData(response.content).forEach(function(el) {
        sensitMotionChart.options.data[0].dataPoints.push(el);
      });
      sensitMotionChart.render();
      nextMotionDataLink = response.links.find(function(el) {
        return el.rel == "next";
      }).href;
    });

    return false;
  }

  function loadMoreTemperatureData() {
    $.get({
      url: nextTemperatureDataLink
    })
    .done(function(response) {
      formatTemperatureData(response.content).forEach(function(el) {
        sensitTemperatureChart.options.data[0].dataPoints.push(el);
      });
      sensitTemperatureChart.render();
      nextTemperatureDataLink = response.links.find(function(el) {
        return el.rel == "next";
      }).href;
    });

    return false;
  }

  $(function() {
    $.get({
      url: "/api/sensors/motion"
    })
    .done(function(response) {
      plotMotion(response.content);
      nextMotionDataLink = response.links.find(function(el) {
        return el.rel == "next";
      }).href;
    });

    $.get({
      url: "/api/sensors/temperature"
    })
    .done(function(response) {
      plotTemperature(response.content);
      nextTemperatureDataLink = response.links.find(function(el) {
        return el.rel == "next";
      }).href;
    });

    $('#load-more-motion').click(loadMoreMotionData);
    $('#load-more-temperature').click(loadMoreTemperatureData);
  });
})();
