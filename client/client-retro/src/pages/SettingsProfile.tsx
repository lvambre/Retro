import NavBar from "../components/NavBar.tsx";
import SettingsNavBar from "../components/SettingsNavBar.tsx";

function SettingsProfile(){
    return (
        <>
            <NavBar/>

            <div className="container text-center">
                <div className="row">
                    <div className="col-md-auto">
                        <SettingsNavBar/>
                    </div>
                    <div className="col col-lg-6">
                        <button type="button" className="btn btn-outline-retro-pink">change username</button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SettingsProfile;