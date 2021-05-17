export 'movie.dart';

import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';

part 'model.g.dart';

@JsonSerializable()
class SessionPriceModel extends Equatable {
  final String name;
  final int lowPrice;
  final int mediumPrice;
  final int highPrice;

  SessionPriceModel(this.name, this.lowPrice, this.mediumPrice, this.highPrice);

  @override
  List<Object> get props => [this.name, this.lowPrice, this.mediumPrice, this.highPrice];

  factory SessionPriceModel.fromJson(Map<String, dynamic> json) =>
      _$SessionPriceModelFromJson(json);

  Map<String, dynamic> toJson() => _$SessionPriceModelToJson(this);
}

@JsonSerializable()
class SeatReservedModel extends Equatable {
  final int seatId;

  SeatReservedModel(this.seatId);


  @override
  List<Object> get props => [this.seatId];

  factory SeatReservedModel.fromJson(Map<String, dynamic> json) =>
      _$SeatReservedModelFromJson(json);

  Map<String, dynamic> toJson() => _$SeatReservedModelToJson(this);
}

@JsonSerializable()
class SeatModel extends Equatable {
  final int id;
  final int row;
  final int number;
  final String seatType;

  SeatModel(this.id, this.row, this.number, this.seatType);


  @override
  List<Object> get props => [this.id, this.row, this.number, this.seatType];

  factory SeatModel.fromJson(Map<String, dynamic> json) =>
      _$SeatModelFromJson(json);

  Map<String, dynamic> toJson() => _$SeatModelToJson(this);

}

@JsonSerializable()
class CinemaHallModel extends Equatable {
  final int id;
  final String name;

  CinemaHallModel(this.id, this.name);


  @override
  List<Object> get props => [this.id, this.name];

  factory CinemaHallModel.fromJson(Map<String, dynamic> json) =>
      _$CinemaHallModelFromJson(json);

  Map<String, dynamic> toJson() => _$CinemaHallModelToJson(this);
}

@JsonSerializable()
class MovieSessionModel extends Equatable {
  final int id;
  final String startDate;
  final CinemaHallModel cinemaHall;
  final SessionPriceModel sessionPrice;
  final List<SeatModel> seats;
  final List<SeatReservedModel> seatsReserved;

  MovieSessionModel(this.id, this.startDate, this.cinemaHall, this.sessionPrice, this.seats, this.seatsReserved);


  @override
  List<Object> get props => [this.startDate, this.cinemaHall, this.sessionPrice, this.seats, this.seatsReserved];

  factory MovieSessionModel.fromJson(Map<String, dynamic> json) =>
      _$MovieSessionModelFromJson(json);

  Map<String, dynamic> toJson() => _$MovieSessionModelToJson(this);

  int priceForSeat(SeatModel seat) {
    if (seat.seatType == "LOW") {
      return sessionPrice.lowPrice;
    }
    if (seat.seatType == "HIGH") {
      return sessionPrice.highPrice;
    }

    return sessionPrice.mediumPrice;
  }

  bool isSeatReserved(SeatModel seat) {
    if (seatsReserved.isEmpty) return false;

    return !seatsReserved.any((element) => element.seatId != seat.id);
  }

}

