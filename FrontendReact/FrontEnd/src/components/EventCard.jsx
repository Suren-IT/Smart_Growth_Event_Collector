//event card 
import React from 'react'
import { markApplied, saveEvent } from '../api/api';

function EventCard({event,email}) {

    const platformName = event.host || event.resource?.name || "NO Platform";
    const handleBookmark = ()=>{
        saveEvent({
            title:event.event,
            platform:event.resource?.name,
            deadline:event.deadline,
            link:event.href,
            useremail:email
        }).then((data)=>{
            if (data === "200") {
                alert("Okey Event Book Marked ");
            }
            else{
                alert("Server Error try again later ")
            }
        });

    };
    const handleApply =()=>{
        markApplied({email,eventName:event.event,status:"applied"})
        .then((data)=>{
            if (data === "200") {
                confirm("data inserted ");
            }
            else{
                 alert("Server Error try again later ")
            }
        })
        .catch((error)=>{
            console.log(error)
        })
        window.open(event.href,"_blank");
    };


  return (
    <div className="events">
      <div className="toppage">
        <div className="top1">
          <img src={event.logo} alt="" />
        </div>
        <div className="top3">
          <h3>{platformName}</h3>
        </div>
        <div className="top2">
          <button onClick={handleBookmark}>
            <i className="fa-regular fa-bookmark"></i>
          </button>
        </div>
      </div>

      <div className="middlepage">
        <div className="middle0">
          <img src={event.logo} alt="picture" />
        </div>
        <div className="middle1">
          <div>
            <h5>{event.event || "no event name"}</h5>
          </div>
          <div className="middledate">
            <h5>Date: {event.start || "data not disclosed"}</h5>
          </div>
        </div>
        <div className="middle2">
          <p>Event details are available. Click Details to learn more.</p>
        </div>
      </div>

      <div className="lastpage">
        <div className="btn1">
          <button onClick={() => openPlatformSite(event.resource?.name)}>
            Details
          </button>
        </div>
        <div className="btn2">
          <button onClick={handleApply}>Apply</button>
        </div>
      </div>
    </div>
  )
}

export default EventCard