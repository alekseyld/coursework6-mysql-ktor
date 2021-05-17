import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_practive6_teacher_info/cubit/cubit.dart';
import 'package:flutter_practive6_teacher_info/model/model.dart';
import 'package:flutter_practive6_teacher_info/model/movie.dart';
import 'package:flutter_practive6_teacher_info/repository/content_repository.dart';

class CinemaListPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<CinemaListCubit, CinemaListState>(
      builder: (context, state) {
        if (state is CinemaListLoaded) {
          return ListView(
            children: state.movies.map((e) => _buildMovieListItem(e)).toList(),
          );
        }

        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }

  Widget _buildMovieListItem(Movie movie) {
    return BlocProvider(
      create: (context) => MovieListItemCubit(
        movie: movie,
        contentRepository: RepositoryProvider.of<ContentRepository>(context),
      ),
      child: BlocBuilder<MovieListItemCubit, MovieListItemState>(
        builder: (context, state) {
          Widget child = ListTile(
            onTap: BlocProvider.of<MovieListItemCubit>(context).expand,
            title: Text(movie.title),
            leading: Image.network(movie.posterUrl),
            subtitle: Text(movie.genre),
          );

          if (state is MovieListItemExpandLoading) {
            child = Column(
              children: [
                child,
                SizedBox(height: 20),
                CircularProgressIndicator(),
              ],
            );
          }

          if (state is MovieListItemExpandLoaded) {
            child = Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                child,
                SizedBox(height: 10),
                Padding(
                  padding: const EdgeInsets.symmetric(
                    vertical: 8,
                    horizontal: 20,
                  ),
                  child: Text("Доступные для бронирования сеансы:"),
                ),
                SizedBox(height: 5),
                ...state.movieSession.map(
                  (e) => _buildMovieSessions(e, context),
                ),
              ],
            );
          }

          return child;
        },
      ),
    );
  }

  Widget _buildMovieSessions(
      MovieSessionModel movieSession, BuildContext context) {
    final List<List<Widget>> tableButton = [];

    movieSession.seats.forEach((seat) {
      if (seat.row > tableButton.length) {
        tableButton.add([]);
      }

      final row = tableButton.elementAt(seat.row - 1);

      row.add(
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: MaterialButton(
            color:
                movieSession.isSeatReserved(seat) ? Colors.red : Colors.green,
            child: Text("${seat.row}:${seat.number + 1}"),
            onPressed: () => BlocProvider.of<MovieListItemCubit>(context)
                .reserveSeat(movieSession, seat),
          ),
        ),
      );
    });

    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text("Дата: ${movieSession.startDate}"),
          Text("Кинозал: ${movieSession.cinemaHall.name}"),
          Text("Всего мест в зале: ${movieSession.seats.length}"),
          Text("Занято мест: ${movieSession.seatsReserved.length}"),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              children: [
                Text("--- Экран ---"),
                SizedBox(height: 8),
                ...tableButton.map(
                  (e) => Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: e,
                  ),
                )
              ],
            ),
          ),
          Divider(),
        ],
      ),
    );
  }
}
