// DASHBOARD DESIGN 

//GET THE ALL EVENTS 
window.addEventListener("load",loadDashboard);

function loadDashboard() {
    const events = JSON.parse(
        localStorage.getItem("allEvent")
    ) || [];

    createPieChart(events);
    console.log(events);
   
}
//PIE CHART
// take the id of canvas
let pieChart = document.getElementById("pieChart");
function createPieChart(event){
    //1.empty object 
    let PlatformCount = {};

    //2.loop through every element and store 
    event.forEach(event=>{

        let name = event.name || event.resource?.name ||
        event.host || "undefined";

        //store the values in object 
        PlatformCount[name] = (PlatformCount[name] || 0)+1;
    });

    let total = Object.values(PlatformCount)
                  .reduce((a, b) => a + b, 0);

    new Chart(
        pieChart ,{
            type:"doughnut",
            data:{
                labels:Object.keys(PlatformCount),
                datasets:[
                    {
                        data:Object.values(PlatformCount),
                        backgroundColor:[
                            "#4F8EF7", "#6BCB77", "#F5A623", "#8B5CF6", "#EC4899"
                        ]
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                cutout:"60%",
                plugins: {
                    legend: {
                       display: false
                    }
                }
            }
        }
        

    )
    let COLORS = ["#4F8EF7", "#6BCB77", "#F5A623", "#8B5CF6", "#EC4899"];
    buildLegend(PlatformCount, total, COLORS);


}

// BUILD LEGEND FOR SIDE BAR 
function buildLegend(platform, total, colors) {
    let legendchart = document.getElementById("legendChart");

    legendchart.innerHTML = "";

    Object.entries(platform).forEach(([name, count], i) => {
        let pct = Math.round((count / total) * 100);
        let color = colors[i % colors.length];

        legendchart.innerHTML += `
           <div style="display:flex; align-items:center; gap:10px; margin-bottom:10px;">
                <span style="width:10px; height:10px; border-radius:50%;
                             background:${color}; flex-shrink:0"></span>
                <span style="flex:1; font-size:13px;">${name}</span>
                <span style="font-size:13px; font-weight:500;">${count}</span>
                <span style="font-size:12px; color:#888;">(${pct}%)</span>
            </div>
        `;
    });
}
