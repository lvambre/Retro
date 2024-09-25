import './App.css'
import "../node_modules/bootstrap-icons/font/bootstrap-icons.css";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home.tsx";
import Orders from "./pages/Orders.tsx";
import Favorites from "./pages/Favorites.tsx";
import SettingsAccount from "./pages/SettingsAccount.tsx";
import Sells from "./pages/Sells.tsx";
import SettingsProfile from "./pages/SettingsProfile.tsx";

function App() {
    return (
        <>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/orders" element={<Orders />} />
                <Route path="/favorites" element={<Favorites />} />
                <Route path="/settings/account" element={<SettingsAccount />} />
                <Route path="/settings/profile" element={<SettingsProfile />} />
                <Route path="/sells" element={<Sells />} />
            </Routes>
        </>
    )
}

export default App
