//register Page 
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { registerUser } from '../api/api';

 const Skill_Options =["java", "python", "sql", "C", "C++", "javascript"];
export default function Register() {
        
        const[form,setForm] = useState({username:"",useremail:"",password:"",role:""});
        const [skills,setSkills] = useState([]);
        const navigate = useNavigate();

        const toogleSkills = (skill) =>{
                setSkills((prev)=>
                    prev.includes(skill) ? prev.filter((s)=> s !== skill) : [...prev,skill]
            );
        }
        const handleSubmit = (e)=>{
            e.preventDefault();
            const {username,useremail,password,role} = form ;
            if (username ===""||useremail===""||password===""||role==="") {
                alert(`Must enter all feilds ${username}`);
                return;
            }
            registerUser({
                name:username,
                email:useremail,
                password,
                role,
                skilllist:skills
            })
            .then((data)=>{
                if (data.status === "200") {
                    navigate("/login");
                }else{
                    alert("User Info is Invalid ")
                }
            }).catch((err)=> alert("Error occurs "));
        }
    

  return (
    <div className="registerbody">
      <h1 id="registerhead">User registration</h1>

      <form className="registerform" onSubmit={handleSubmit}>
        <table className="formtable">
          <tbody>
            <tr>
              <td>
                <label htmlFor="username">Enter User Name :</label>
              </td>
              <td>
                <input
                  id="username"
                  type="text"
                  placeholder="username"
                  value={form.username}
                  onChange={(e) => setForm({ ...form, username: e.target.value })}
                />
              </td>
            </tr>
            <tr>
              <td>
                <label htmlFor="useremail">Enter User email :</label>
              </td>
              <td>
                <input
                  id="useremail"
                  type="email"
                  placeholder="useremail"
                  value={form.useremail}
                  onChange={(e) => setForm({ ...form, useremail: e.target.value })}
                />
              </td>
            </tr>
            <tr>
              <td>
                <label htmlFor="password">Enter password :</label>
              </td>
              <td>
                <input
                  id="password"
                  type="password"
                  placeholder="pass"
                  value={form.password}
                  onChange={(e) => setForm({ ...form, password: e.target.value })}
                />
              </td>
            </tr>
            <tr>
              <td>
                <label htmlFor="role">Enter User role :</label>
              </td>
              <td>
                <input
                  id="role"
                  type="text"
                  placeholder="role"
                  value={form.role}
                  onChange={(e) => setForm({ ...form, role: e.target.value })}
                />
              </td>
            </tr>
            <tr>
              <td style={{ color: "aliceblue" }}>Skills:</td>
              <td className="skillcheckbox">
                {SKILL_OPTIONS.map((skill) => (
                  <label key={skill}>
                    {skill}{" "}
                    <input
                      type="checkbox"
                      checked={skills.includes(skill)}
                      onChange={() => toggleSkill(skill)}
                    />
                  </label>
                ))}
              </td>
            </tr>
          </tbody>
        </table>
        <hr className="line" />
        <Link to="/login" className="logintag">
          Already a member ?
        </Link>
        <br />
        <button type="submit" id="registerbtn">
          submit
        </button>
      </form>
    </div>
  )
}

export default Register