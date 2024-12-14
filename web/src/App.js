import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import axios from 'axios';

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
        <div>
            <h1>Games List</h1>
            <ul>
                {games.map(game => (
                    <li key={game.id}>{game.name}</li>
                ))}
            </ul>
        </div>
    );
}

export default App;