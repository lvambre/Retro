function NavBar2(){
    return (
        <>
            <nav className="navbar navbar-expand-lg border-bottom">
                <div className="container-fluid">
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <div className="navbar-nav">
                            <a className="nav-link" href="#">Women</a>
                            <a className="nav-link" href="#">Men</a>
                            <a className="nav-link" href="#">Kids</a>
                            <a className="nav-link" href="#">Tops</a>
                            <a className="nav-link" href="#">Bottoms</a>
                            <a className="nav-link" href="#">Shoes</a>
                            <a className="nav-link" href="#">Activewear</a>
                        </div>
                    </div>
                </div>
            </nav>
        </>
    )
}

export default NavBar2;