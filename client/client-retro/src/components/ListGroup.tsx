import './ListGroup.css'
function ListGroup() {

    let items = [
        'New York',
        'Dijon',
        'Plougourvest',
        'Partenay',
        'Guada'
    ]
    items = [];

    const getMessage = () => {
        return items.length === 0 && <p>No item found</p>;
    }
    return (
        <>
            <h1>List</h1>
            { getMessage() }
            <ul className="list-group">
                {items.map((item) => (
                    <li key={item}>{item}</li>
                ))}
            </ul>
        </>
    );
}

export default ListGroup;