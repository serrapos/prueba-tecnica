import React from 'react';
import { NavLink } from "react-router-dom";

export class Header extends React.Component {
    render() {
       return (
        <nav className="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
            <a className="navbar-brand" href="/">Prueba Técnica Iberia - Sergio Raposo Vargas</a>            

            <div className="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/" exact>Home</NavLink>                        
                    </li>
                    <li className="nav-item">
                        <NavLink className="nav-link" to="/new-course/">Nuevo</NavLink>
                    </li>                    
                </ul>
                <div className="my-2 my-lg-0">
                    <span className="text-light">Everis - Junio 2019</span>
                </div>
            </div>
        </nav>
       );
    }
 }