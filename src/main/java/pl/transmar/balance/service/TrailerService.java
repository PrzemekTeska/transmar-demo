package pl.transmar.balance.service;

import pl.transmar.balance.model.Trailer;

import java.util.List;

public interface TrailerService {

    List<Trailer> getAllTrailers();

    Trailer addTrailer(Trailer trailer);

    Trailer updateTrailer(long id, Trailer newTrailer);

    void deleteTrailer(long id);

    Trailer getTrailerById(long id);
}
