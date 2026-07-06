
//AUTOMATICALLY LOAD THE EVENTS 
window.addEventListener("load",()=>{
    console.log("loading is started ");
    loadAllEvents();
    console.log("loading is ended ")

    

});
let email = localStorage.getItem("email");
let allEvent=[];
//HOME PAGE WHEN IT IS LOADING 
async function loadAllEvents() {
    
    try {
        
        container.innerHTML="";

        //create a list of events 
        const appList=[
            {
                url:"http://localhost:8080/hackerearth",
                logo:"/frontend/images/HackerEarth-Logo.jpg"
            },
            {
                url:"http://localhost:8080/leetcode",
                logo:"/frontend/images/LeetCode_logo.png"
            },
            {
                url:"http://localhost:8080/hackerrank",
                logo:"/frontend/images/hackerrank-logo.jpg"
            },
            {
                url:"http://localhost:8080/codecheff",
                logo:"/frontend/images/HackerEarth-Logo.jpg"
            },
            {
                url:"http://localhost:8080/codeforces",
                logo:"/frontend/images/CodeForces_Cover.jpg"
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

       localStorage.setItem("allEvent",JSON.stringify(allEvent));
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
                        <button onclick="savedEvents('${eventdetails.event}', '${eventdetails.resource?.name}', '${eventdetails.start}', '${eventdetails.href}')"><i class="fa-regular fa-bookmark"></i></button>
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
                        <button onclick="callPlatform('${eventdetails.resource.name}')">
                                Details
                        </button>
                    </div>

                    <div class="btn2">
                        <button onclick="appliedData('${email}', '${eventdetails.event}', 'applied'); window.open('${eventdetails.href}', '_blank')">
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
let searchbtn = document.getElementById("searchbtn");

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
                         <div class="top2">
                                <button onclick="savedEvents('${eventdetails.event}', '${eventdetails.resource?.name}', '${eventdetails.start}', '${eventdetails.href}')"><i class="fa-regular fa-bookmark"></i></button>
                         </div>
                        
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

                        <div class="btn1">
                             <button onclick="callPlatform('${eventdetails.resource.name}')">
                                Details
                            </button>
                        </div>

                        <div class="btn2">
                            <button onclick="appliedData('${email}', '${eventdetails.event}', 'applied'); window.open('${eventdetails.href}', '_blank')">
                                Apply
                            </button>
                        </div>

                </div>

                </div>
                </div>
        `;

    });
    container.innerHTML += html;
};


//search btn
searchbtn.addEventListener("click",()=>{
        if ((document.getElementById("searchinput").value == "" && document.getElementById("platforminput").value=="") || document.getElementById("duration").value == ""  ) {
            alert("please enter something");
        }
        else{
            searching();
        }
        
});

//hackerearth btn 
hackerearth.addEventListener("click",()=>{

        fetchEvents("http://localhost:8080/hackerearth" , "/frontend/images/HackerEarth-Logo.jpg");

});
//leetcodebtn
leetcode.addEventListener("click",()=>{

        fetchEvents("http://localhost:8080/leetcode" , "/frontend/images/LeetCode_logo.png");

});

//hackerrank btn 
hackrank.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/hackerrank" , "/frontend/images/hackerrank-logo.jpg")
});
//codecheff
codecheff.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/codecheff" , "/frontend/images/codechef-logo-.jpg")
});
//codeforce 
codeforces.addEventListener("click",()=>{
        fetchEvents("http://localhost:8080/codeforces" , "/frontend/images/CodeForces_Cover.jpg")
});


function searching() {
    let keyword = document.getElementById("searchinput").value.toLowerCase();
    let platform = document.getElementById("platforminput").value.toLowerCase();
        let fillterEvents = allEvent.filter(eventdetails =>
            eventdetails.event
            .toLowerCase()
            .includes(keyword) || 
            eventdetails.resource.name
            .toLowerCase()
            .includes(platform)
        );
        console.log(fillterEvents);
        renderMixedEvents(fillterEvents);
}




// SAVED Events

function savedEvents(title,platform,deadline,link){

    let email = localStorage.getItem("email");
    
    
    fetch("http://localhost:8080/addevent" , {
        method: "POST",
        headers: {                    
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            platform: platform,
            deadline: deadline,
            link: link,
            useremail : email
        })
        
     })
     .then(response => response.json())
     .then(data =>{
        if (data.status === "200") {
            
            alert("okey book marked ");
        }
        else{
            
            alert("something went wrong ");
        }
     })
     .catch(err => {
        console.error("Fetch error:", err);
        alert("Network error.");
    });
}

function appliedData(email,eventname,status){

    fetch("http://localhost:8080/applied",
        {
            method: "POST",
            headers:{
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                eventName: eventname,
                status: status,
                
            })
        })
        .then(response => response.json())
        .then(data =>{
            if(data.status === "200"){
                console.log(status);
                confirm("data inserted");
            }
            else{
                alert("something happens ")
            }
        })
        .catch(error =>{
            console.log(error);
            alert("error occurs");
        })
}

function callPlatform(platform){
    console.log(platform);

    switch(platform.toLowerCase()){
        case "leetcode":
            window.open("https://leetcode.com");
            break;
        case "codechef":
            window.open("https://codechef.com");
            break;
        case "hackerearth":
            window.open("https://hackerearth.com");
            break;
        case "hackerrank":
            window.open("https://hackerrank.com");
            break;
        case "codeforces":
            window.open("https://codeforces.com");
            break;
        default:
            console.log("error occurs ");
    }
}