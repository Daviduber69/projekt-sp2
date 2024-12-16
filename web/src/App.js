import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from 'axios';
import {Link, Route, BrowserRouter, Routes, useParams} from "react-router-dom";

const App = () => {
    const [games, setGames] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/games')
            .then(response => {
                setGames(response.data.games);
            })
            .catch(error => {
                console.error("Could not fetch games!: ", error);
            })
    }, []);

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={
                    <div>
                        <h1>Games List</h1>
                        <ul>
                            {games.map(game => (
                                <li key={game.id}>
                                    <Link to={`/game/${game.name}`}> {game.name} </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                }
                />
                <Route path="/game/:name" element={<GameDetails />}></Route>
            </Routes>
        </BrowserRouter>
    );
};

const GameDetails = () => {
    const {name} = useParams();
    const [game, setGame] = useState();
    const [error, setError] = useState(null);
    useEffect(() => {
        axios.get(`http://localhost:8080/game/${name}`)
            .then(response => setGame(response.data))
            .catch(error => {
                console.error("Could not fetch games!: ", error);
                setError("Game not found!");
            })
    }, [name]);
    if (error) {
        return <h2>{error}</h2>;
    }
    if (!game) {
        return <h2>Loading...</h2>;
    }
    return(
        <>
        <h1>{game.name}</h1>
        <p> {game.genre}</p>
            <p>{game.developer}</p>
            <p>${game.price}</p>
        </>
    )
}

export default App;