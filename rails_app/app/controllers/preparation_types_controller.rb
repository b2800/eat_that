class PreparationTypesController < ApplicationController
  before_action :set_preparation_type, only: [:show, :edit, :update, :destroy]

  # GET /preparation_types
  # GET /preparation_types.json
  def index
    @preparation_types = PreparationType.all
  end

  # GET /preparation_types/1
  # GET /preparation_types/1.json
  def show
  end

  # GET /preparation_types/new
  def new
    @preparation_type = PreparationType.new
  end

  # GET /preparation_types/1/edit
  def edit
  end

  # POST /preparation_types
  # POST /preparation_types.json
  def create
    @preparation_type = PreparationType.new(preparation_type_params)

    respond_to do |format|
      if @preparation_type.save
        format.html { redirect_to @preparation_type, notice: 'Preparation type was successfully created.' }
        format.json { render :show, status: :created, location: @preparation_type }
      else
        format.html { render :new }
        format.json { render json: @preparation_type.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /preparation_types/1
  # PATCH/PUT /preparation_types/1.json
  def update
    respond_to do |format|
      if @preparation_type.update(preparation_type_params)
        format.html { redirect_to @preparation_type, notice: 'Preparation type was successfully updated.' }
        format.json { render :show, status: :ok, location: @preparation_type }
      else
        format.html { render :edit }
        format.json { render json: @preparation_type.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /preparation_types/1
  # DELETE /preparation_types/1.json
  def destroy
    @preparation_type.destroy
    respond_to do |format|
      format.html { redirect_to preparation_types_url, notice: 'Preparation type was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_preparation_type
      @preparation_type = PreparationType.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def preparation_type_params
      params.require(:preparation_type).permit(:name)
    end
end
