part of 'movie_list_item_cubit.dart';

abstract class MovieListItemState extends Equatable {
  const MovieListItemState();
}

class MovieListItemCollapsed extends MovieListItemState {
  @override
  List<Object> get props => [];
}

class MovieListItemExpandLoading extends MovieListItemState {
  @override
  List<Object> get props => [];
}

class MovieListItemExpandLoaded extends MovieListItemState {
  final List<MovieSessionModel> movieSession;

  MovieListItemExpandLoaded(this.movieSession);

  @override
  List<Object> get props => [movieSession];
}

