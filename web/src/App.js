import './App.css';
import {useEffect, useState} from "react";
import axios from 'axios';
import {Link, Route, BrowserRouter, Routes, useParams, useNavigate} from "react-router-dom";

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
    const [username, setUsername] = useState("");
    const [rating, setRating] = useState(0);
    const [reviewContent, setReviewContent] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();



    const handleSubmit = (e) => {
        e.preventDefault();

        if (!reviewContent || !rating) {
            return;
        }

        const url = `http://localhost:8081/game/${encodeURIComponent(name)}/reviews`;
        const payload = {
            reviewerName: username,
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
    };
    const handleName = (e) => {
        setUsername(e.target.value);
    };
    return (
        <div>
            <Link to={`/`}><button>Back to home</button></Link>
            <h1>Write a Review for {name}</h1>

            <a href="http://localhost:8083/website/users/create" target="_blank" rel="noopener noreferrer">
                Register User:
            </a>
            <br/>
            <input type="text" value={username} onChange={handleName} placeholder="Enter your name" />

            {error && <p style={{color: 'red'}}>{error}</p>}
            <form onSubmit={handleSubmit}>
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
            <Link to={`/`}><button>Back to home</button></Link>
            <h1>Review Details</h1>
            <h3>Reviewer: {review.reviewerName}</h3>
            <p>{Array(review.rating).fill('★').join('')} ({review.rating}/5)</p>
            <p><strong></strong> {review.reviewContent}</p>
        </div>
    );
}

export default App;