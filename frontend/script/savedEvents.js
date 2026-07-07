let myeventlist = document.getElementById("myeventbar");
myeventlist = "";
//I will create a card for my saved events 
fetch("http://localhost:8080/savedevents")
.then(response => response.json())
.then(data =>{
     
     myeventlist= `
     
               <div class="events">

                    <div class="toppage">
                        <div class="top1"><img src="${data.logo}" alt=""></div>
                        <div class="top3"><h3>${data.eventdetails.host ||eventdetails.resource.name || "NO Platform"}</h3></div>
                         <div class="top2">
                                <button onclick="savedEvents('${eventdetails.event}', '${eventdetails.resource?.name}', '${eventdetails.start}', '${eventdetails.href}')"><i class="fa-regular fa-bookmark"></i></button>
                         </div>
                        
                    </div>
                    <div class="middlepage">
                        <div class="middle0">
                            <img src="${data.logo}" alt="picture" >
                        </div>
                        <div class="middle1">
                            <div><h5>${data.eventdetails.event || "no event anem"}</h5></div>
                            <div class="middledate">
                                <h5>date:${data.eventdetails.start || "data not disclosed "}</h5>
                                <h5></h5>

                            </div>
                        </div>
                        <div class="middle2">
                            <p>Event details are available. Click Details to learn more.</p>
                        </div>
                        
                    </div>
                    <div class="lastpage">

                        <div class="btn1">
                             <button onclick="callPlatform('${data.eventdetails.resource.name}')">
                                Details
                            </button>
                        </div>

                        <div class="btn2">
                            <button onclick="appliedData('${data.email}', '${data.eventdetails.event}', 'applied'); window.open('${eventdetails.href}', '_blank')">
                                Apply
                            </button>
                        </div>

                </div>

                </div>
                </div>
     `
})