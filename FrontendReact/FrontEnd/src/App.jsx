import React from 'react'
import {BrowserRouter,Routes,Route,Navigate} from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Home from './pages/Home'



function App() {
  return (
    <>
        <BrowserRouter >
          <Route  path="/" element={<Navigate to="/login" replace />}/>
          <Route  path="/" element={<Login/>} />
          <Route path="/register" element={<Register />} />
          <Route path="/home" element={<Home />} />
        </BrowserRouter>
    </>
  )
}

export default App