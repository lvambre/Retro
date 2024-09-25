import NavBar from "../components/NavBar.tsx";
import SettingsNavBar from "../components/SettingsNavBar.tsx";

function SettingsAccount(){
    return (
        <>
            <NavBar/>

            <div className="container text-center">
                <div className="row">
                    <div className="col-md-auto justify-content-start">
                        <SettingsNavBar/>
                    </div>
                    <div className="col">
                        <a className="btn btn-retro-blue" href="/home" role="button">Delete account</a>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SettingsAccount;