import retro from '../assets/retro.png'

function NavBar(){
    return (
        <>
            <nav className="navbar navbar-expand-lg border-bottom">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/">
                        <img src={retro} className="logo" alt="Retro logo" height="35"/>
                    </a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                            aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
                        <form className="d-flex mx-auto" role="search">
                            <input className="form-control me-2" type="search" placeholder="Search"
                                   aria-label="Search"/>
                            <button className="btn btn-outline-retro-pink" type="submit">Search</button>
                        </form>
                        <div className="navbar-nav">
                            <a className="nav-link" href="/orders">
                                <i className="bi-box-seam"/>
                                Orders
                            </a>
                            <a className="nav-link" href="/favorites">
                                <i className="bi-heart-fill"/>
                                Favorites
                            </a>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    <i className="bi-person-circle"/>
                                    Account
                                </a>
                                <ul className="dropdown-menu">
                                    <li><a className="dropdown-item" href="/settings">Settings</a></li>
                                    <li><a className="dropdown-item" href="/sells">Sells</a></li>
                                    <li><hr className="dropdown-divider"/></li>
                                    <li><a className="dropdown-item" href="#">Sign in/Sign up</a></li>
                                </ul>
                            </li>
                        </div>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default NavBar;