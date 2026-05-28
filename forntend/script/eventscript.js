// this is event script 
//steps to create the event 
/* 
    1.create the div(events),div(toppage),div(middlepage),div(lastpage)
    2.inside that also have mulitiple div 
*/

let searchbtn = document.getElementById("serachbtn");
let container= document.getElementById("eventpageid");
searchbtn.addEventListener("click",()=>{



    fetch("http://localhost:8080/dataofapi")
    .then((response => response.json()))
    .then((data)=>{
        console.log(data);
        //get the data 
        data.hackathons.forEach( eventdetails=> {
            
            container.innerHTML +=`
                <div class="events">

                    <div class="toppage">
                        <div class="top1"><img src="/images/loginbg.jpg" alt=""></div>
                        <div class="top2"><i class="fa-solid fa-magnifying-glass"></i></div>
                        
                    </div>
                    <div class="middlepage">
                        <div class="middle1">
                            <h5 class="event-name">${eventdetails.title}</h5>
                        </div>
                        <div class="middle2">
                            <p class="event-desc">Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore itaque sunt eaque quasi culpa a vitae saepe maxime! Accusantium odio, quos fugiat vitae ipsum, unde minus, repellat voluptatem perspiciatis dignissimos distinctio at! Maiores, placeat eligendi quo incidunt nihil, nesciunt error sapiente, quas vel est quisquam voluptas quam odio consectetur eos?</p>
                        </div>
                        
                    </div>
                    <div class="lastpage">
                        <div class="btn1"><button>Details</button></div>
                        <div class="btn2"><button>Apply</button></div>
                    </div>
                </div>    
            `;
        });


    }).catch(error=>{
        console.log(error);
        alert("something went wrong ");
    })

})