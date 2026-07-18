// Central place for every backend call.
const BASE_URL = "http://localhost:8080";

//login user 
export async function loginUser(email,password) {
    const result = await fetch(`${BASE_URL}/login`,{
        method:"POST",
        headers:{
            "Content-Type":"application/x-www-form-urleccoded"
        },
        body:`email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`
    });
    return result.json();
}

//register user 
export async function registerUser(details) {
    const res= await fetch(`${BASE_URL}/register`,{
        method:"POST",
        headers:{
            "Content-Type": "application/json"
        },
        body:JSON.stringify(details)
    });
    return res.json();
}

// -----------Event ------------------
export const PLATFORMS = [
  { key: "hackerearth", url: `${BASE_URL}/hackerearth`, logo: "/images/HackerEarth-Logo.jpg" },
  { key: "leetcode", url: `${BASE_URL}/leetcode`, logo: "/images/LeetCode_logo.png" },
  { key: "hackerrank", url: `${BASE_URL}/hackerrank`, logo: "/images/hackerrank-logo.jpg" },
  { key: "codecheff", url: `${BASE_URL}/codecheff`, logo: "/images/codechef-logo-.jpg" },
  { key: "codeforce", url: `${BASE_URL}/codeforces`, logo: "/images/CodeForces_Cover.jpg" },
];

export async function fetchAllEvents() {
    const response = await Promise.all(PLATFORMS.map((p)=> fetch(p.url)));
    const dataList = await Promise.all(response.map((r)=>r.json()));

    const allEvents= [];
    dataList.forEach((data,index)=>{
        data.objects.forEach((event) =>{
            allEvents.push({...event,logo:PLATFORMS[index].logo});
        })
    })
    allEvents.sort((a,b)=> new Date(a.start) - new Date(b.start));
    return allEvents;
}

export async function fetchByPlatform(platfomKey) {
    const platfom = PLATFORMS.find((p)=> p.key == platfomKey);
    if(!platfom) throw new Error("Unknown Platform")
    const res = await fetch(platfom.url);
    const data = await res.json();
    return data.objects.map((event) => ({...event,logo:platfom.logo}));
}

//Save Event 
export async function saveEvent({title,platform,deadline, link, useremail}) {
    const res = await fetch(`${BASE_URL}/addevent`,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({title,platform,deadline, link, useremail})
    })
    return res.json();
    
}
