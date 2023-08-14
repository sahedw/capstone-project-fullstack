import {useState} from 'react';
import Hamburger from "hamburger-react";
import {NavLink} from "react-router-dom";

function BurgerMenu() {
    const [isOpen, setOpen] = useState<boolean>(false)
    return (<>
            <section className={`fixed-burger-menu ${isOpen ? "menu-active" : ""}`}>
                <Hamburger toggled={isOpen}
                           toggle={setOpen}
                           direction={"right"}
                           color={"black"}
                           label="Show menu"/>
            </section>
            <section className={`off-canvas-menu ${isOpen ? "menu-open" : "menu-closed"}`}>
                <nav className={"navbar-container"}>
                    <ul className={"navbar-list"}>
                        <NavLink to={"/"}
                                 className={({ isActive }) =>
                                     isActive ? "active-link" : "inactive-link"
                                 }>
                            <li>HOME</li>
                        </NavLink>
                        <NavLink to={"/addFoodSpot"}
                                 className={({ isActive }) =>
                                     isActive ? "active-link" : "inactive-link"
                                 }>
                            <li>ADD FOODSPOTS</li>
                        </NavLink>
                        <NavLink to={"/map"}
                                 className={({ isActive }) =>
                                     isActive ? "active-link" : "inactive-link"
                                 }>
                            <li>MAP</li>
                        </NavLink>
                        <NavLink to={"/account"}
                                 className={({ isActive }) =>
                                     isActive ? "active-link" : "inactive-link"
                                 }>
                            <li>MY ACCOUNT</li>
                        </NavLink>
                    </ul>
                </nav>
            </section>
        </>

    );
}

export default BurgerMenu;
