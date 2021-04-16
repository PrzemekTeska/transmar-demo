package pl.transmar.balance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.transmar.balance.model.Trailer;
import pl.transmar.balance.repositories.TrailerRepo;

import java.util.List;

@Service
public class TrailerServiceImplementation implements TrailerService {

    private TrailerRepo trailerRepo;

    @Autowired
    public TrailerServiceImplementation(TrailerRepo trailerRepo) {
        this.trailerRepo = trailerRepo;
    }

    @Override
    public List<Trailer> getAllTrailers() {
        return trailerRepo.findAll();
    }

    @Override
    public Trailer addTrailer(Trailer trailer) {
        trailer.setNumber(trailer.getNumber().toUpperCase());
        trailerRepo.save(trailer);
        return trailer;
    }

    @Override
    public Trailer updateTrailer(long id, Trailer newTrailer) {
        Trailer trailer = trailerRepo.findById(id).orElse(null);
        if(trailer != null) {
            trailer.setNumber(newTrailer.getNumber().toUpperCase());
            trailerRepo.save(trailer);
        }
        return trailer;
    }

    @Override
    public void deleteTrailer(long id) {
        trailerRepo.deleteById(id);
    }

    @Override
    public Trailer getTrailerById(long id) {
        return trailerRepo.findById(id).orElse(null);
    }
}
