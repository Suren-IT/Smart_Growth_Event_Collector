// DASHBOARD DESIGN 

//GET THE ALL EVENTS 
window.addEventListener("load",loadDashboard);

 async function loadDashboard() {
    const events = JSON.parse(
        localStorage.getItem("allEvent")
    ) || [];

    createPieChart(events);
    createLineChart(events);
    createUpcoming(events);
    createInfoBoxes(events);
    //to calclulate the saved events 
    // console.log(events);
        appliedEvents();
       savedEvents(); 
   
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

// LINE CHART 
function createLineChart(event){

    let monthCount = {};

    

    event.forEach(event => {

        let date =
            event.date?.split("T")[0] ||
            event.start?.split("T")[0];

        if (!date) return;

        let d = new Date();

        // example: 2026-6
        let key = `${d.getFullYear()}-${d.getMonth() + 1}`; 
        

        monthCount[key] = (monthCount[key] || 0) + 1;
    });

    // last 6 months range 
    let labels=[];
    let counts=[];

    let today = new Date();
    for(let i=0;i<6;i++){
        let d = new Date();

        d.setMonth(today.getMonth()+i);

        let key = `${d.getFullYear()}-${d.getMonth() + 1}`; 

        labels.push(
            d.toLocaleDateString("en-US",{
                month:"short"
            })
        );
        counts.push(monthCount[key] || 0);

    }

    // LINE CHART DRAWING 
    new Chart(lineChart, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Events",
                data: counts,
                borderColor: "#7F77DD",
                backgroundColor: "rgba(127,119,221,0.12)",
                borderWidth: 2,
                pointBackgroundColor: "#7F77DD",
                pointBorderColor: "#ffffff",
                pointBorderWidth: 2,
                pointRadius: 5,
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            },
            scales: {
                x: {
                    grid: { display: false }
                },
                y: {
                    beginAtZero:true
                }
            }
        }
    });
}

// UPCOMING EVENTS 
function createUpcoming(events) {

    let today = new Date();
    today.setHours(0, 0, 0, 0);

    // 1. FILTER FUTURE EVENTS ONLY
    let upcoming = events.filter(event => {

        let dateStr =
            event.date?.split("T")[0] ||
            event.start?.split("T")[0];

        if (!dateStr) return false;

        let eventDate = new Date(dateStr);

        return eventDate <= today;
    });

    // 2. SORT BY NEAREST DATE
    upcoming.sort((a, b) => {
        return new Date(a.date || a.start) - new Date(b.date || b.start);
    });

    // console.log(upcoming);

    // 3. TAKE ONLY TOP 5 (LIKE DASHBOARD CARD STYLE)
    upcoming = upcoming.slice(-5);
    // 4. RENDER TO UI
    let container = document.getElementById("upcoming");

    container.innerHTML = upcoming.map(event => {

        let date = new Date(event.date || event.start);

        return `
            <div class="upcoming-card">
                <div>
                    <img src="${event.logo}" alt="">
                </div>
                <div>
                     <p>${event.event || "Online"}</p>
                    <p>${event.resource.name || "Event"}</p>

                </div>
                <div>
                    <p>${date.toDateString()}</p>
                </div>
                <div>
                    <button id="eventlink">${event.resource.name || "Event"}</button>
                </div>
                
                <div>
                    <button id="viewid"><a href="${event.href} ">VIEW</a></button>
                </div>
                
                
               
            </div>
        `;
    }).join("");
    
}


// INFO BOXES 
function createInfoBoxes(event){

    let card = document.getElementById("classbar");

    card.innerHTML ="";

    //return the inner html 
    card.innerHTML +=`
        <div class="classifer-card">
                    <div class="cardright"><i  class="fa-regular fa-bell"></i></div>
                    <div class="cardleft">
                        <p>Total events </p>
                        <p>${event.length}</p>
                        <p>All upcming events</p>
                    </div>

        </div>
        <div class="classifer-card">
                    <div class="cardright"><i  class="fa-regular fa-bell"></i></div>
                    <div class="cardleft">
                        <p>Applied Events</p>
                        <p id="appliedeventid" ">0</p>
                        <p>event this month</p>
                    </div>

        </div>
        <div class="classifer-card">
                    <div class="cardright"><i  class="fa-regular fa-bell"></i></div>
                    <div class="cardleft">
                        <p>Saved event</p>
                        <p id="savedEventid">0</p>
                        <p>your saved event</p>
                    </div>

        </div>
        <div class="classifer-card">
                    <div class="cardright"><i  class="fa-regular fa-bell"></i></div>
                    <div class="cardleft">
                        <p>Participated event</p>
                        <p></p>
                        <p>your Participation</p>
                    </div>

        </div>
        <div class="classifer-card">
                    <div class="cardright"><i  class="fa-regular fa-bell"></i></div>
                    <div class="cardleft">
                        <p>win rate</p>
                        <p></p>
                        <p>success rate </p>
                    </div>

        </div>
    `
}
// calculate the this week functions
function thismonth(event) {
    
    //find the this week event and take the length 
    let  eventCount =0;

    let month = new Date().getMonth();
    event.forEach(event=>{

        let mounthnumber = event.start?.split("T")[0].slice(6,8);
        if (mounthnumber  == month) {
            // console.log(monthCount);
            eventCount+=1;
        }

    })

    return eventCount;

}

// SAVED EVENTS

function savedEvents() {
    let savedEventid = document.getElementById("savedEventid");
    if (!savedEventid) {
        console.log("NULL Values")
        return;
    }
    fetch(`http://localhost:8080/finduserlist/${localStorage.getItem("email")}`)
   .then(response => response.json())
   .then(data =>{
     if (data){

        savedEventid.innerHTML += data;
     }
     else{
        return 0;
     }
   })
   .catch(error =>{
    console.log(error);
   })
}


function appliedEvents() {
    
let appliedeventid = document.getElementById("appliedeventid");
if(!appliedEvents){
    console.log("wrong");
    return;

}
   fetch(`http://localhost:8080/countofApplied/${localStorage.getItem("email")}`)
   .then(response => response.json())
   .then(data =>{
     if (data){
        
        appliedeventid.innerText=data;
     }
     else{
        return 0;
     }
   })
}