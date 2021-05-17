import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_practive6_teacher_info/model/model.dart';
import 'package:flutter_practive6_teacher_info/repository/content_repository.dart';

part 'movie_list_item_state.dart';

class MovieListItemCubit extends Cubit<MovieListItemState> {
  final ContentRepository contentRepository;
  final Movie movie;

  MovieListItemCubit({
    required this.movie,
    required this.contentRepository,
  }) : super(MovieListItemCollapsed());

  void expand() async {
    if (state is MovieListItemExpandLoaded) {
      emit(MovieListItemCollapsed());
      return;
    }

    emit(MovieListItemExpandLoading());

    final List<MovieSessionModel> sessions = await contentRepository.fetchMovieSession(movie.id);

    emit(MovieListItemExpandLoaded(sessions));

  }

  void reserveSeat(MovieSessionModel movieSession, SeatModel seat) async {

    emit(MovieListItemExpandLoading());

    await contentRepository.reserveSeat(movieSession.id, seat.id);

    expand();

  }
}
