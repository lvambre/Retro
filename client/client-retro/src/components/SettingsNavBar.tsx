function SettingsNavBar(){
    return(
        <>
            <nav className="navbar text-start">
                <div className="navbar-nav">
                    <a className="nav-link" href="/settings/profile">Profile</a>
                    <a className="nav-link" href="/settings/account">Account's details</a>
                </div>
            </nav>
        </>
    )
}

export default SettingsNavBar;