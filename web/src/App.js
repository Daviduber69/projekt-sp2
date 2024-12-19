import './App.css';
import {useEffect, useState} from "react";
import axios from 'axios';
import {Link, Route, BrowserRouter, Routes, useParams, useNavigate} from "react-router-dom";
import barbie from './BARBIE.png';
import poe from './POE2.png';
import slither from './SLITHER.png';
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


    const [addPublisher, setAddPublisher] = useState({
        name: "",
        country: ""
    });
    const [addGame, setAddGame] = useState({
        name: "",
        genre: "",
        price: 0,
        publisherId: null
    });

    const [successMessage, setSuccessMessage] = useState("");

    const gamesUrl = 'http://localhost:8080/games';
    const publisherUrl = 'http://localhost:8080/publisher'


        const handleAddGame = async(e) => {
            e.preventDefault()
            try {
                const publisherResp = await axios.post(publisherUrl, addPublisher);

                if (!publisherResp.data) {
                    throw new Error("Failed to retrieve publisher ID from response.");
                }
                const publisher = publisherResp.data;
                const newGame = {
                    ...addGame,
                    publisher
                };
                const gameResp = await axios.post(gamesUrl, newGame);

                setGames((prevGames) => [...prevGames, gameResp.data]);
                setSuccessMessage("Successfully added game!");
                setAddPublisher({ name: "", country: "" });
                setAddGame({ name: "", genre: "", price: 0, publisherId: null });
            }catch (error) {
                console.error("Error adding game or publisher: ", error);
            }
        }
    const handleGameChange = (e) => {
        setAddGame((prev) =>( {
            ...prev,
            [e.target.name]: e.target.value
        }))
    };
    const handlePublisherChange = (e) => {
        setAddPublisher((prev) =>( {
            ...prev,
            [e.target.name]: e.target.value
        }))
    };

    return (
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={
                        <div>
                            <h1>Games List</h1>
                            <ul>
                                {games.map(game => (
                                    <div key={game.id}>
                                        <Link to={`/game/${game.name}`}><h2> {game.name}</h2> </Link>
                                    </div>
                                ))}
                            </ul>

                            <div style={{
                                position: 'fixed',
                                bottom: '10px',
                                left: '10px',
                                padding: '10px',
                            }}>
                                <form onSubmit={handleAddGame}>
                                    <h2>ADD NEW GAMES BELOW: </h2>
                                    <input type="text" name="name" value={addPublisher.name} onChange={handlePublisherChange}
                                           placeholder="Publisher name: "/>
                                    <br/>
                                    <input type="text" name="country" value={addPublisher.country} onChange={handlePublisherChange}
                                           placeholder="Publisher country: "/>
                                    <br/>
                                    <input type="text" name="name" value={addGame.name} onChange={handleGameChange}
                                           placeholder="Game name: "/>
                                    <br/>
                                    <input type="text" name="genre" value={addGame.genre} onChange={handleGameChange}
                                           placeholder="Game genre: "/>
                                    <br/>
                                    <input type="text" name="price" value={addGame.price} onChange={handleGameChange}
                                           placeholder="Game price: "/>
                                    <br/>
                                    <button type="submit">Submit Game</button>
                                </form>
                                {successMessage && <h3>{successMessage}</h3> }

                                <h4>Quick Links</h4>
                                <ul style={{listStyleType: 'none', padding: 0}}>
                                    <li><a href="http://localhost:8080/games" target="_blank"
                                           rel="noopener noreferrer">http://localhost:8080/games</a></li>
                                    <li><a href="http://localhost:8081/reviews" target="_blank"
                                           rel="noopener noreferrer">http://localhost:8081/reviews</a></li>
                                    <li><a href="http://localhost:8083/users" target="_blank"
                                           rel="noopener noreferrer">http://localhost:8083/users</a></li>
                                </ul>
                            </div>

                        </div>
                    }
                    />
                    <Route path="/game/:name" element={<GameDetails/>}></Route>
                    <Route path="/game/:name/write-review" element={<WriteReview/>}></Route>
                    <Route path="/game/:name/review/:reviewId" element={<ReviewDetails/>}/>
                </Routes>
            </BrowserRouter>
        );
    };



const GameDetails = () => {
    const {name} = useParams();
    const [game, setGame] = useState();
    const [reviews, setReviews] = useState([]);
    const [error, setError] = useState(null);
    useEffect(() => {
        axios.get(`http://localhost:8080/game/${name}`)
            .then(response => setGame(response.data))
            .catch(error => {
                console.error("Could not fetch games!: ", error);
                setError("Game not found!");
            })
    }, [name]);

    useEffect(() => {
        axios.get(`http://localhost:8081/game/${name}/reviews`)
            .then(response => setReviews(response.data))
            .catch(error => {
                console.error("Could not fetch reviews!", error);
                setError("Could not fetch reviews!");
            });
    }, [name]);

    const handleVote = (reviewId, voteType) => {
        axios.post(`http://localhost:8081/reviews/${reviewId}/vote`, {
            voterName: 'Anonymous',
            voteType: voteType
        })
            .then(response => {
                setReviews(prevReviews => prevReviews.map(review =>
                    review.id === reviewId ? {...review, voteCount: response.data.voteCount} : review
                ));
            })
            .catch(error => {
                console.error("Could not register vote!", error);
            });
    };

    if (error) {
        return <h2>{error}</h2>;
    }
    if (!game) {
        return <h2>Loading...</h2>;
    }
    return (
        <>
            <Link to={`/`}><button>Back to home</button></Link>

            <h1>{game.name}</h1>
            <p> {game.genre}</p>
            <p>Publisher: {game.publisher.name}, {game.publisher.country}</p>
            <p>${game.price}</p>
            {game.name === "Barbie and the Magic of Pegasus" ? (
                <img src={barbie} alt="Barbie" />
            ): game.name === "Path of Exile 2" ? (
                <img src={poe} alt="POE2" />
                ) : game.name === "Slither.io" ? (
                    <img src={slither} alt="Slither" />
            ): null

            }
            <br/>
            <Link to={`/game/${game.name}/write-review`}><button>Write Review</button></Link>


            <h2>Reviews</h2>
            {reviews.length === 0 ? (
                <p>No reviews yet for this game.</p>
            ) : (
                <ul>
                    {reviews.map((review) => (
                        <li key={review.id} style={{marginBottom: '10px'}}>
                            <Link to={`/game/${name}/review/${review.id}`}>
                                <strong>{review.reviewerName}</strong> -
                                {Array(review.rating).fill('★').join('')}
                                ({review.rating}/5)
                            </Link>
                            <button onClick={() => handleVote(review.id, 'UPVOTE')}>👍</button>
                            <button onClick={() => handleVote(review.id, 'DOWNVOTE')}>👎</button>
                            <span>Votes: {review.voteCount || 0}</span>
                        </li>
                    ))}
                </ul>
            )}

        </>
    )
}

const WriteReview = () => {
    const {name} = useParams();
    const [user, setUser] = useState({
        name: "",
        email: "",
    });

    const [address, setAddress] = useState({
        street: "",
        city: "",
        state: "",
        postalCode: "",
        country: "",
    });
    const [rating, setRating] = useState(0);
    const [reviewContent, setReviewContent] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();



    const handleSubmit = (e) => {
        e.preventDefault();

        if (!reviewContent || !rating) {
            return;
        }
        const userUrl = 'http://localhost:8083/users';
        const addressUrl = 'http://localhost:8083/address'

        const url = `http://localhost:8081/game/${encodeURIComponent(name)}/reviews`;
        const payload = {
            reviewerName: user.name,
            reviewContent: reviewContent,
            rating: rating

        };

        axios.post(url, payload)
            .then((response) => {
                console.log('Review submitted successfully:', response.data);
                navigate(`/game/${name}`);
            })
            .catch((error) => {
                console.error("Could not submit review!", error);

            });

        axios.post(userUrl, user)
            .then((response) => {
                console.log('User created:', response.data);
            })
            .catch((error) => {
                console.error('Error saving user:', error);
            });
        axios.post(addressUrl, address)
            .then((response) => {
                console.log('Address saved:', response.data);
            })
            .catch((error) => {
                console.error('Error saving address:', error);
            })
    };


        const handleUserChange = (e) => {
           setUser(prev =>( {
                ...prev,
               [e.target.name]: e.target.value
           }))
        };

        const handleAddressChange = (e) => {
           setAddress(prev =>({
               ...prev,
               [e.target.name]: e.target.value
           }))
        };

    return (
        <div>
            <Link to={`/`}>
                <button>Back to home</button>
            </Link>
            <h1>Write a Review for {name}</h1>



            {error && <p style={{color: 'red'}}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input
                        name="name"
                        type="text"
                        value={user.name}
                        onChange={handleUserChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    Email:
                    <input
                        name="email"
                        type="email"
                        value={user.email}
                        onChange={handleUserChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    Street:
                    <input
                        name="street"
                        type="text"
                        value={address.street}
                        onChange={handleAddressChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    City:
                    <input
                        name="city"
                        type="text"
                        value={address.city}
                        onChange={handleAddressChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    State:
                    <input
                        name="state"
                        type="text"
                        value={address.state}
                        onChange={handleAddressChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    Postal Code:
                    <input
                        name="postalCode"
                        type="text"
                        value={address.postalCode}
                        onChange={handleAddressChange}
                        required
                    />
                </label>
                <br/>
                <label>
                    Country:
                    <input
                        name="country"
                        type="text"
                        value={address.country}
                        onChange={handleAddressChange}
                        required
                    />
                </label>
                <br/>
                <div>
                    <h3>Rating</h3>
                    {[1, 2, 3, 4, 5].map(star => (
                        <span
                            key={star}
                            onClick={() => setRating(star)}
                            style={{
                                cursor: 'pointer',
                                color: star <= rating ? 'gold' : 'gray',
                                fontSize: '2rem'
                            }}
                        >
                            ★
                        </span>
                    ))}
                </div>
                <div>
                    <h3>Review</h3>
                    <textarea
                        value={reviewContent}
                        onChange={(e) => setReviewContent(e.target.value)}
                        placeholder="Write your review here..."
                        rows="5"
                        cols="40"
                    ></textarea>
                </div>
                <button type="submit">Submit Review</button>
            </form>
        </div>
    );
}


const ReviewDetails = () => {
    const {name, reviewId} = useParams();
    const [review, setReview] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        const url = `http://localhost:8081/game/${encodeURIComponent(name)}/reviews/${reviewId}`;
        console.log(`Requesting Review from: ${url}`);

        axios.get(url)
            .then(response => {
                setReview(response.data);
            })
            .catch(error => {
                console.error("Could not fetch review details!", error);
                setError("Review not found!");
            });
    }, [name, reviewId]);

    if (error) {
        return <h2>{error}</h2>;
    }

    if (!review) {
        return <h2>Loading...</h2>;
    }

    return (
        <div>
            <Link to={`/`}>
                <button>Back to home</button>
            </Link>
            <h1>Review Details</h1>
            <h3>Reviewer: {review.reviewerName}</h3>
            <p>{Array(review.rating).fill('★').join('')} ({review.rating}/5)</p>
            <p><strong></strong> {review.reviewContent}</p>
        </div>
    );
}

export default App;