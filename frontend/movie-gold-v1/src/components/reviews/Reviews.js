import React, { useEffect, useRef } from 'react';
import api from '../../api/axiosConfig';
import { useParams } from 'react-router-dom';
import { Container, Col, Row } from 'react-bootstrap';
import ReveiwForm from '../reviewForm/ReviewForm';

const Reviews = ({getMovieData, movie, reviews, setReviews}) => {

    const revText = useRef(); // reference the text area in review form
    let params = useParams(); //extract the movieId param value from the url
    const movieId = params.movieId; 

    useEffect(() => {
        getMovieData(movieId);
    }, [movieId])

    const addReview = async (e) => {
        e.preventDefault();

        const rev = revText.current;

        try{
            const response = await api.post('/api/v1/reviews', {reviewBody: rev.value, imdbId:movieId});
            // console.log("Here: " + JSON.stringify(response.data, null, 2));

            const updatedReviews = reviews!=null? [...reviews, {body: rev.value}]:[{body: rev.value}];
            // const updatedReviews = [...reviews, {body:rev.value}];

            rev.value = '';
    
            setReviews(updatedReviews);

        } catch(err) {
            console.error(err);
        }
       
    }

    return (
        <Container>
            <Row>
                <Col><h3>Reviews</h3></Col>
            </Row>
            <Row className="mt-2">
                <Col>
                    <img src={movie?.poster} alt="Movie Poster" />
                </Col>
                <Col>
                    {
                        <>
                            <Row>
                                <Col>
                                    <ReveiwForm handleSubmit={addReview} revText={revText} labelText='Write a Review' />
                                </Col>
                            </Row>
                            <Row>
                                <Col>
                                    <hr />
                                </Col>
                            </Row>
                        </>
                    }
                    {
                        reviews?.map((r, index) => {

                            return(
                                <div key={index} >
                                <Row>
                                    <Col>{r.body}</Col>
                                </Row>
                                <Row>
                                    <Col>
                                        <hr />
                                    </Col>
                                </Row>                                
                            </div>
                            )
                        })
                    }
                </Col>
            </Row>
        </Container>
    )
}

export default Reviews