
//AUTOMATICALLY LOAD THE EVENTS 
window.addEventListener("load",()=>{
    console.log("loading is started ");
    loadAllEvents();

});
let allEvent=[];
//HOME PAGE WHEN IT IS LOADING 
async function loadAllEvents() {
    
    try {
        
        container.innerHTML="";

        //create a list of events 
        const appList=[
            {
                url:"http://localhost:8080/hackerearth",
                logo:"/images/HackerEarth-Logo.jpg"
            },
            {
                url:"http://localhost:8080/leetcode",
                logo:"/images/LeetCode_logo.png"
            },
            {
                url:"http://localhost:8080/hackerrank",
                logo:"/images/hackerrank-logo.jpg"
            },
            {
                url:"http://localhost:8080/codecheff",
                logo:"/images/HackerEarth-Logo.jpg"
            },
            {
                url:"http://localhost:8080/codeforces",
                logo:"/images/CodeForces_Cover.jpg"
            }
        ];

        //after to push events 
        allEvent=[];

        let response = await Promise.all(
            appList.map(api => fetch(api.url))
        );


        let dataList = await Promise.all(
            response.map(response => response.json())
        );

        //get datalist data 
        dataList.forEach((data,index)=>{
            data.objects.forEach(event=>{
                event.logo = appList[index].logo;

                allEvent.push(event);
            });
        });

        //sorting to  upcoming event 
        allEvent.sort((a,b)=>{
            return new Date(a.start) - new Date(b.start);
        });

       renderMixedEvents(allEvent);

    } catch (error) {
        alert("Error occures ")
    }
    
}

//MIXED EVENTS RENDERFUNCTION FOR THE LODING PURPOSE 
function renderMixedEvents(events){

    let html = "";

    events.forEach(eventdetails=>{

        html += `

            <div class="events">

                <div class="toppage">

                    <div class="top1">
                        <img src="${eventdetails.logo}" alt="">
                    </div>

                    <div class="top3">
                        <h3>
                            ${eventdetails.host ||
                              eventdetails.resource?.name ||
                              "No Platform"}
                        </h3>
                    </div>

                    <div class="top2">
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </div>

                </div>

                <div class="middlepage">

                    <div class="middle0">

                        <img src="${eventdetails.logo}" alt="picture">

                    </div>

                    <div class="middle1">

                        <div>

                            <h5>
                                ${eventdetails.event}
                            </h5>

                        </div>

                        <div class="middledate">

                            <h5>
                                Date :
                                ${eventdetails.start}
                            </h5>

                        </div>

                    </div>

                    <div class="middle2">

                        <p>
                        Event details are available.
                        Click Details to learn more.
                        </p>

                    </div>

                </div>

                <div class="lastpage">

                    <div class="btn1">
                        <button>
                            Details
                        </button>
                    </div>

                    <div class="btn2">
                        <button onclick="window.open('${eventdetails.href}','_blank')">
                            Apply
                        </button>
                    </div>

                </div>

            </div>

        `;

    });

    container.innerHTML = html;

}

// this is for search button
let searchbtn = document.getElementById("serachbtn");

//hackerearth fillter 
let hackerearth = document.getElementById("hackerearth");

//leetcode button
let leetcode = document.getElementById("leetcode");

//hackerrank
let hackrank = document.getElementById("hackerrank");

//codecheff
let codecheff = document.getElementById("codecheff");

//codeforces
let codeforces = document.getElementById("codeforce")
//events
let container= document.getElementById("eventpageid");

// FETCH THE API WITH FUNCTION FOR REDUNTENT USE 
async function fetchEvents(url,logo) {
    
    try {
        
        //clear the old events 
        container.innerHTML ="";

        const response = await fetch(url);

        if (!response) {
            alert("api is not working ");

        }
        const data = await response.json();

        //call the eventCreation method to use 
        renderEvents(data.objects,logo);

    } catch (error) {
        alert("error occurs ")
    }
}

function renderEvents(events,logo){
    let html="";
    events.forEach(eventdetails =>{
        html +=`
                <div class="events">

                    <div class="toppage">
                        <div class="top1"><img src="${logo}" alt=""></div>
                        <div class="top3"><h3>${eventdetails.host ||eventdetails.resource.name || "NO Platform"}</h3></div>
                        <div class="top2"><i class="fa-solid fa-magnifying-glass"></i></div>
                        
                    </div>
                    <div class="middlepage">
                        <div class="middle0">
                            <img src="${logo}" alt="picture" >
                        </div>
                        <div class="middle1">
                            <div><h5>${eventdetails.event || "no event anem"}</h5></div>
                            <div class="middledate">
                                <h5>date:${eventdetails.start || "data not disclosed "}</h5>
                                <h5></h5>

                            </div>
                        </div>
                        <div class="middle2">
                            <p>Event details are available. Click Details to learn more.</p>
                        </div>
                        
                    </div>
                    <div class="lastpage">
                        <div class="btn1"><button>Details</button></div>
                        <div class="btn2"><button>Apply</button></div>
                    </div>
                </div>
        `;

    });
    container.innerHTML += html;
};


//search btn
searchbtn.addEventListener("click",()=>{
        if (document.getElementById("searchinput").value == "") {
            alert("please enter something");
        }
        else{
            searching();
        }
        
});

//hackerearth btn 
hackerearth.addEventListener("click",()=>{

        fetchEvents("http://localhost:8080/hackerearth" , "/images/HackerEarth-Logo.jpg");

});
//leetcodebtn
leetcode.addEventListener("click",()=>{

        fetchEvents("http://localhost:8080/leetcode" , "/images/LeetCode_logo.png");

});

//hackerrank btn 
hackrank.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/hackerrank" , "/images/hackerrank-logo.jpg")
});
//codecheff
codecheff.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/codecheff" , "/images/codechef-logo-.jpg")
});
//codeforce 
codeforces.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/codeforces" , "/images/CodeForces_Cover.jpg")
});


function searching() {
    let keyword = document.getElementById("searchinput").value.toLowerCase();
        let fillterEvents = allEvent.filter(eventdetails =>
            eventdetails.event
            .toLowerCase()
            .includes(keyword)
        );
        console.log(fillterEvents);
        renderMixedEvents(fillterEvents);
}
